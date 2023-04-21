'use strict';
const BaseGenerator = require('../base-generator');
const constants = require('../constants');
const prompts = require('./prompts');
const _ = require('lodash');
// const { template } = require('lodash');
// const { append } = require('express/lib/response');

// var lodash = require('lodash');
// exports.index = function(req, res) {
//     res.render('index', { lodash: lodash });
// }

module.exports = class extends BaseGenerator {

    constructor(args, opts) {
        super(args, opts);
        this.configOptions = this.options.configOptions || {};
        console.log("CONTROLLER CALLED !!!");
        // this.argument("entityName", {
        //     type: String,
        //     required: true,
        //     description: "Entity name"
        // });

        // this.option('base-path', {
        //     type: String,
        //     desc: "Base URL path for REST Controller"
        // })
    }

    get initializing() {
        this.logSuccess('Generating JPA entity, repository, service and controller');
        return {
            validateEntityName() {
                const context = this.context;
                console.log(`EntityName: ${this.options.entityName}, apiBasePath: ${this.options.apiBasePath}`);
                //this.env.error("The entity name is invalid");
            }
        }
    }
/*
    get prompting() {
        return prompts.prompting;
        //    askForDomainLayer: prompts.askForDomainLayer,
        //    askForFields: prompts.askForFields
    }
*/
   
    
    configuring() {
        this.configOptions = Object.assign({}, this.configOptions, this.config.getAll());
       // this.configOptions.apiBasePath = this.options['base-path'];
       if(this.configOptions.withDomainLayer === false){
       function getOnetoOneMappingColumnNames(relationships){
        let columnNames=[];
        for(let relationship of relationships){
            if(relationship.relationshipType == 'one-to-one'){
                columnNames.push(_.replace(_.lowerCase(relationship.relationshipName),/ /g,"_")+"_id");
            }
        }
        return columnNames;
       }
        this.configOptions.apiBasePath = this.options.apiBasePath;
        this.configOptions.entityName = this.options.entityName;
        this.configOptions.entitiesMetaData = this.options.entitiesMetaData;
        this.configOptions.moduleName = _.lowerCase(this.configOptions.moduleName);
        this.configOptions.entityVarName = _.camelCase(this.options.entityName);
        this.configOptions.tableName =_.replace(_.lowerCase(this.options.entityName),/ /g,"_");
        this.configOptions.supportDatabaseSequences =
            this.configOptions.databaseType === 'h2'
            || this.configOptions.databaseType === 'postgresql';
        if(this.configOptions.withDomainLayer === false){
        this.configOptions.columnNames = getOnetoOneMappingColumnNames(this.configOptions.relationships);
        }
    }else{
            this.configOptions.apiBasePath = this.options.apiBasePath;
            this.configOptions.entityName = this.options.entityName;
            this.configOptions.entitiesMetaData = this.options.entitiesMetaData;
            this.configOptions.moduleName = this.configOptions.moduleName.toLowerCase();
            this.configOptions.entityVarName = _.camelCase(this.options.entityName);
            this.configOptions.tableName =_.replace(_.lowerCase(this.options.entityName),/ /g,"_");
            this.configOptions.supportDatabaseSequences =
                this.configOptions.databaseType === 'h2'
                || this.configOptions.databaseType === 'postgresql';
    }
    }

    writing() {
        this._generateAppCode(this.configOptions);
        this._generateDbMigrationConfig(this.configOptions)
    }

    end() {
        //TODO; Disabling this temporarily to fix test failures.
        //this._formatCode(this.configOptions);
    }

    _generateAppCode(configOptions) {
        /*
        const mainJavaTemplates = [
            {src: 'entities/Entity.java', dest: 'entities/'+configOptions.entityName+'.java'},
            {src: 'repositories/Repository.java', dest: 'repositories/'+configOptions.entityName+'Repository.java'},
            {src: 'services/Service.java', dest: 'services/'+configOptions.entityName+'Service.java'},
            {src: 'web/controllers/Controller.java', dest: 'web/controllers/'+configOptions.entityName+'Controller.java'},
            {src: 'modules/commands/Command.java', dest: 'modules/'+configOptions.entityVarName+'/commands/'+configOptions.entityName+'Command.java'},
        ];
        */
        var mainJavaTemplates=[];
        
        if(configOptions.withDomainLayer === false){
        if(!configOptions.isAttributeClass){
         mainJavaTemplates = [
            {src: 'modules/commands/Command.java', dest: 'modules/'+configOptions.moduleName+'/commands/'+configOptions.entityName+'Command.java'},
            {src: 'modules/commands/CommandRequestDto.java', dest: 'modules/'+configOptions.moduleName+'/commands/'+configOptions.entityName+'CommandRequestDto.java'},
            {src: 'modules/commands/CommandResponseDto.java', dest: 'modules/'+configOptions.moduleName+'/commands/'+configOptions.entityName+'CommandResponseDto.java'},
            {src: 'modules/commands/CommandService.java', dest: 'modules/'+configOptions.moduleName+'/commands/'+configOptions.entityName+'CommandService.java'},
            {src: 'modules/commands/CommandHttpController.java', dest: 'modules/'+configOptions.moduleName+'/commands/'+configOptions.entityName+'CommandHttpController.java'},
            {src: 'modules/infrastructure/OrmEntity.java', dest: 'modules/'+configOptions.moduleName+'/infrastructure/'+configOptions.entityName+'OrmEntity.java'},
            {src: 'modules/infrastructure/PersistancyPort.java', dest: 'modules/'+configOptions.moduleName+'/infrastructure/I'+configOptions.entityName+'PersistancyPort.java'},
            {src: 'modules/infrastructure/Repository.java', dest: 'modules/'+configOptions.moduleName+'/infrastructure/'+configOptions.entityName+'Repository.java'},
            {src: 'modules/infrastructure/RepositoryAdapter.java', dest: 'modules/'+configOptions.moduleName+'/infrastructure/'+configOptions.entityName+'RepositoryAdapter.java'},
            {src: 'modules/queries/Query.java', dest: 'modules/'+configOptions.moduleName+'/queries/'+configOptions.entityName+'Query.java'},
            {src: 'modules/queries/QueryRequestDto.java', dest: 'modules/'+configOptions.moduleName+'/queries/'+configOptions.entityName+'QueryRequestDto.java'},
            {src: 'modules/queries/QueryResponseDto.java', dest: 'modules/'+configOptions.moduleName+'/queries/'+configOptions.entityName+'QueryResponseDto.java'},
            {src: 'modules/queries/QueryService.java', dest: 'modules/'+configOptions.moduleName+'/queries/'+configOptions.entityName+'QueryService.java'},
            {src: 'modules/queries/QueryHttpController.java', dest: 'modules/'+configOptions.moduleName+'/queries/'+configOptions.entityName+'QueryHttpController.java'},
            {src: 'commons/converters/Mapper.java', dest: 'commons/converters/'+configOptions.entityName+'Mapper.java'},
            {src: 'modules/infrastructure/exceptions/DuplicateEntryException.java', dest: 'modules/'+configOptions.moduleName+'/infrastructure/exceptions/DuplicateEntryException.java'},
            {src: 'modules/infrastructure/exceptions/FieldNotFoundException.java', dest: 'modules/'+configOptions.moduleName+'/infrastructure/exceptions/FieldNotFoundException.java'},
            {src: 'modules/infrastructure/exceptions/ResourceNotFoundException.java', dest: 'modules/'+configOptions.moduleName+'/infrastructure/exceptions/ResourceNotFoundException.java'},
        ];
        }else{
            mainJavaTemplates = [
                {src: 'modules/commands/Dto.java', dest: 'modules/'+configOptions.moduleName+'/commands/'+configOptions.entityName+'Dto.java'},
                {src: 'modules/queries/Dto.java', dest: 'modules/'+configOptions.moduleName+'/queries/'+configOptions.entityName+'Dto.java'}, 
                {src: 'modules/infrastructure/OrmEntity.java', dest: 'modules/'+configOptions.moduleName+'/infrastructure/'+configOptions.entityName+'OrmEntity.java'},   
            ];  
        }
}

var domainLayerTemplates = [];
var otherModuleDtosTemplate =[];
var ormEntitiesTemplates =[];
var valueObjectTemplate=[];

if(configOptions.withDomainLayer === true){

         domainLayerTemplates =[
            {src: 'modulewithdomain/commands/Command.java', dest: 'modules/'+configOptions.moduleName+'/commands/'+configOptions.entityName+'Command.java'},
            {src: 'modulewithdomain/commands/CommandRequestDto.java', dest: 'modules/'+configOptions.moduleName+'/commands/'+configOptions.entityName+'CommandRequestDto.java'},
            {src: 'modulewithdomain/commands/CommandResponseDto.java', dest: 'modules/'+configOptions.moduleName+'/commands/'+configOptions.entityName+'CommandResponseDto.java'},
            {src: 'modulewithdomain/commands/CommandService.java', dest: 'modules/'+configOptions.moduleName+'/commands/'+configOptions.entityName+'CommandService.java'},
            {src: 'modulewithdomain/commands/CommandHttpController.java', dest: 'modules/'+configOptions.moduleName+'/commands/'+configOptions.entityName+'CommandHttpController.java'},
            {src: 'modulewithdomain/domain/AggregateRoot.java.ejs', dest: 'modules/'+configOptions.moduleName+'/domain/'+configOptions.entityName+'AggregateRoot.java'},
            {src: 'modulewithdomain/queries/Query.java', dest: 'modules/'+configOptions.moduleName+'/queries/'+configOptions.entityName+'Query.java'},
            {src: 'modulewithdomain/queries/QueryRequestDto.java', dest: 'modules/'+configOptions.moduleName+'/queries/'+configOptions.entityName+'QueryRequestDto.java'},
            {src: 'modulewithdomain/queries/QueryResponseDto.java', dest: 'modules/'+configOptions.moduleName+'/queries/'+configOptions.entityName+'QueryResponseDto.java'},
            {src: 'modulewithdomain/queries/QueryService.java', dest: 'modules/'+configOptions.moduleName+'/queries/'+configOptions.entityName+'QueryService.java'},
            {src: 'modulewithdomain/queries/QueryHttpController.java', dest: 'modules/'+configOptions.moduleName+'/queries/'+configOptions.entityName+'QueryHttpController.java'},
            {src: 'commons/converters/Mapper.java', dest: 'commons/converters/'+configOptions.entityName+'Mapper.java'},
            {src: 'modulewithdomain/infrastructure/PersistancyPort.java', dest: 'modules/'+configOptions.moduleName+'/infrastructure/'+'I'+configOptions.entityName+'PersistancyPort.java'},
            {src: 'modulewithdomain/infrastructure/Repository.java', dest: 'modules/'+configOptions.moduleName+'/infrastructure/'+configOptions.entityName+'Repository.java'},
            {src: 'modulewithdomain/infrastructure/RepositoryAdapter.java.ejs', dest: 'modules/'+configOptions.moduleName+'/infrastructure/'+configOptions.entityName+'RepositoryAdapter.java'} 
        ];

         otherModuleDtosTemplate = [
            {src: 'commons/dtos/dto.java', dest: 'commons/dtos/'+configOptions.importModuleDtos.name+'.java'}
        ];

        if(this.options.generateOnlyValueObject === true){
        valueObjectTemplate = [
            {src: 'commons/valueobjects/ValueObject.java', dest: 'commons/valueobjects/'+configOptions.valueObject.valueObjectName+'.java'}
        ];}

         ormEntitiesTemplates = [
            {src: 'modulewithdomain/infrastructure/OrmEntity.java', dest: 'modules/'+configOptions.moduleName+'/infrastructure/'+configOptions.ormEntities.name+'OrmEntity.java'}
        ];
    }
        const historyTableTemplates =[
            {src: 'modules/queries/HistoryResponseDto.java', dest: 'modules/'+configOptions.moduleName+'/queries/'+configOptions.entityName+'HistoryResponseDto.java'},
            {src: 'modules/infrastructure/HistoryOrmEntity.java', dest: 'modules/'+configOptions.moduleName+'/infrastructure/'+configOptions.entityName+'HistoryOrmEntity.java'},
            {src: 'modules/infrastructure/HistoryRepository.java', dest: 'modules/'+configOptions.moduleName+'/infrastructure/'+configOptions.entityName+'HistoryRepository.java'},
        ]

        if(configOptions.addHistoryTable===true){
            historyTableTemplates.forEach(template=>{
                mainJavaTemplates.push(template);
            });
        }

        if(configOptions.withDomainLayer){
            if(this.options.generateOnlyImportDtos === true){
                otherModuleDtosTemplate.forEach(template=>{
                    mainJavaTemplates.push(template);
                });
            }
            if(this.options.generateAggregateRoot === true){
                domainLayerTemplates.forEach(template=>{
                    mainJavaTemplates.push(template);
                });
            }
            if(this.options.generateOnlyOrmLayer === true){
                ormEntitiesTemplates.forEach(template=>{
                    mainJavaTemplates.push(template);
                });
            }     
            if(this.options.generateOnlyValueObject === true){
                valueObjectTemplate.forEach(template=>{
                    mainJavaTemplates.push(template);
                });
            }
        }

        this.generateMainJavaCode(configOptions, mainJavaTemplates);

        const testJavaTemplates = [
            {src: 'web/controllers/ControllerTest.java', dest: 'web/controllers/'+configOptions.entityName+'ControllerTest.java'},
            {src: 'web/controllers/ControllerIT.java', dest: 'web/controllers/'+configOptions.entityName+'ControllerIT.java'},
        ];
        if(this.configOptions.withDomainLayer===false){
        this.generateTestJavaCode(configOptions, testJavaTemplates);
        }
    }

    _generateDbMigrationConfig(configOptions) {
        if(this.configOptions.withDomainLayer===false){
        if(configOptions.dbMigrationTool === 'flywaydb') {
            this._generateFlywayMigration(configOptions)
        }

        if(configOptions.dbMigrationTool === 'liquibase') {
            this._generateLiquibaseMigration(configOptions);
        }
    }
    }

    _generateFlywayMigration(configOptions) {
        const counter = configOptions[constants.KEY_FLYWAY_MIGRATION_COUNTER] + 1;
        let vendor = configOptions.databaseType;
        if(vendor === "mariadb") {
            vendor = "mysql";
        }
        const scriptTemplate = configOptions.supportDatabaseSequences ?
            "V1__new_table_with_seq.sql" : "V1__new_table_no_seq.sql";

        this.fs.copyTpl(
            this.templatePath('app/src/main/resources/db/migration/flyway/V1__new_table_with_seq.sql'),
            this.destinationPath('src/main/resources/db/migration/h2/V'+counter+'__create_'+configOptions.tableName+'_table.sql'),
            configOptions
        );
        this.fs.copyTpl(
            this.templatePath('app/src/main/resources/db/migration/flyway/'+scriptTemplate),
            this.destinationPath('src/main/resources/db/migration/'+vendor+
                '/V'+counter+'__create_'+configOptions.tableName+'_table.sql'),
            configOptions
        );
        const flywayMigrantCounter = {
            [constants.KEY_FLYWAY_MIGRATION_COUNTER]: counter
        };
        this.config.set(flywayMigrantCounter);
    }

    _generateLiquibaseMigration(configOptions) {
        function getFieldsNameInColumnNamingConvention(fields){
            let fieldsAsPerConventions=[];
            for(let field of fields){
                  let entry={};
                    entry.fieldName= _.replace(_.lowerCase(field.fieldName),/ /g,"_");
                    if(field.validation.isClob === undefined || field.validation.isClob === false){
                    entry.fieldType= field.fieldType; 
                    }else{
                    entry.fieldType='clob';}
                    fieldsAsPerConventions.push(entry);  
            }
            return fieldsAsPerConventions;
           }
        this.configOptions.fieldsAsPerConventions = getFieldsNameInColumnNamingConvention(this.configOptions.fields);
        const counter = configOptions[constants.KEY_LIQUIBASE_MIGRATION_COUNTER] + 1;
        const scriptTemplate = configOptions.supportDatabaseSequences ?
            "01-new_table_with_seq.xml" : "01-new_table_no_seq.xml";
        //table for each entity
        this.fs.copyTpl(
            this.templatePath('app/src/main/resources/db/migration/liquibase/changelog/'+scriptTemplate),
            //this.destinationPath('src/main/resources/db/migration/changelog/0'+counter+'-create_'+configOptions.tableName+'_table.xml'),
            this.destinationPath('src/main/resources/db/migration/changelog/create_'+configOptions.tableName+'_table.xml'),
            configOptions
        );
                //mapping table generation
                if(configOptions.relationships !== []){
                    for(const relationship of configOptions.relationships){
                        if(relationship.relationshipType !== 'one-to-one'){
                           // if(relationship.ignoreOtherSide === true){
                            let fileName = configOptions.tableName+'_'+relationship.relationshipName+'_mapping';
                            configOptions.fileName=fileName;
                            configOptions.relationshipName = relationship.relationshipName;
                            this.fs.copyTpl(
                                this.templatePath('app/src/main/resources/db/migration/liquibase/changelog/mapping_table.xml'),
                                this.destinationPath('src/main/resources/db/migration/changelog/'+fileName+'_table.xml'),
                                configOptions
                            );
                           // }
                        }
                    }
                }

        //history table generation
        if(configOptions.addHistoryTable===true){
            let fileName = configOptions.tableName+'_history';
            configOptions.fileName=fileName;
            this.fs.copyTpl(
                this.templatePath('app/src/main/resources/db/migration/liquibase/changelog/history_table.xml'),
                this.destinationPath('src/main/resources/db/migration/changelog/'+fileName+'_table.xml'),
                configOptions);
        }
        
        const liquibaseMigrantCounter = {
            [constants.KEY_LIQUIBASE_MIGRATION_COUNTER]: counter
        };
        //const updatedConfig = Object.assign({}, this.config.getAll(), liquibaseMigrantCounter);
        this.config.set(liquibaseMigrantCounter);
    }
};
