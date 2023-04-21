const bodyParser = require('body-parser');
const check = require('express-validator').check;
var express = require('express');
var app = express();
const { body, validationResult } = require('express-validator');
const { interfaces } = require('mocha');
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
var yeoman = require('yeoman-environment');

    app.get('/generate-entity',(req,res)=>{
        
        const MODULES= [{
          moduleName: 'User',
          withDomainLayer: false,
          apiBasePath: 'example/v1/user',
          entities: [
            {
              entityName: 'User',
              //apiBasePath: 'example/v1/user',
              //withDomainLayer: false,
              isAttributeClass: false,
              fields:[
                 {
                  fieldName: 'first_name',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^[a-zA-Z ]*$',
                    length: 50,
                    isEmail: false,
                  }
                 },
                 {
                  fieldName: 'last_name',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^[a-zA-Z ]*$',
                    length: 50,
                    isEmail: false,
                  }
                 },
                 {
                  fieldName: 'email',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$',
                    length: 50,
                    isEmail: true,
                  }
                },
              ],
              relationships: [
                 {
                   otherEntityName: "Address",
                   otherEntityRelationshipName: "User",
                   ownerSide: true,
                   relationshipName: "address",
                   relationshipType: "one-to-many",
                   //ignoreOtherSideProperty: true,
                   //ownerSide: true,
                   validation: {
                    notEmpty: false,
                    notNull: true,
                    isEmail: false,
                    isValid: true,
                  }
                 }
               ],
             },
             {
              entityName: 'Address',
              //apiBasePath: 'example/v1/user',
              //withDomainLayer: false,
              isAttributeClass: true,
              fields:[
                 {
                  fieldName: 'full_address',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^[a-zA-Z ]*$',
                    length: 50,
                    isEmail: false,
                  }
                 },
                 {
                  fieldName: 'city',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^[a-zA-Z ]*$',
                    length: 50,
                    isEmail: false,
                  }
                 },
                 {
                  fieldName: 'state',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^[a-zA-Z ]*$',
                    length: 50,
                    isEmail: false,
                  }
                },
                {
                  fieldName: 'country',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^[a-zA-Z ]*$',
                    length: 50,
                    isEmail: false,
                  }
                },
              ],
              relationships: [],
             }
          ],
        }];
      
        for (const MODULE of MODULES){
          const moduleName = MODULE.moduleName;
          const withDomainLayer =MODULE.withDomainLayer;
          const apiBasePath = MODULE.apiBasePath;
           for (const ENTITY of MODULE.entities){
          const entityName = ENTITY.entityName;
          ENTITY.moduleName= moduleName;
          ENTITY.withDomainLayer = withDomainLayer;
         // const apiBasePath = ENTITY.apiBasePath;   
          const env = yeoman.createEnv([],{cwd: 'C://Aman//generated-projects//ciamV2'});
          env.register(require.resolve('../generators/controller/index'), 'npm:controller');
          env.run("npm:controller", {skipInstall: true,entityName,apiBasePath,configOptions: ENTITY }).then(result => {
           runResult = result;
           });
          }}

     res.end("Working");
     });

    
app.get('/service',(req,res)=>{  
    const SERVICE ={
      appName: "Fulkrum",
      serviceName: "UserManagement",
      groupId: "com.crisil",
      artifactId: "usermanagement",                      
      packageName: "com.crisil.fulkrum.usermanagement",
      databaseType: "mysql" ,                             
      dbMigrationTool: "liquibase",           
      cloudFeatures : ["elk","localstack","monitoring"],
      buildTool: "maven"
    };
      
    const env = yeoman.createEnv([],{cwd: 'C://Aman//generated-projects'});
    env.register(require.resolve('../generators/app/index'), 'npm:app');
    env.run("npm:app", {skipInstall: true, configOptions: SERVICE}).then(result => {
     runResult = result;
     });
     res.json(SERVICE);
     });

     app.get('/check',(req,res)=>{

      String.prototype.replaceLast = function (search, replace) {
          return this.replace(new RegExp(search+"([^"+search+"]*)$"), replace+"$1");
      }
      
      str = "getFrameworkByNameAndVersionAnd";
      newStr = str.replaceLast("And", "");
      console.log(newStr);
       });


     app.post('/create-service',
     
     body('service.appName')
     .exists()
     .withMessage('Application Name is required')
     .isLength({ min:2, max:50 })
     .withMessage('Name should be greator than 2 and less than 50')
     .matches(/^([a-zA-Z_][a-zA-Z0-9_\-]*)$/)
     .withMessage('The application name you have provided is not valid'),

     body('service.packageName')
     .exists()
     .withMessage('Package Name is required')
     .isLength({ min:2, max:50 })
     .withMessage('Name should be greator than 2 and less than 50')
     .matches(/^([a-z_][a-z0-9_]*(\.[a-z_][a-z0-9_]*)*)$/)
     .withMessage('The package name you have provided is not a valid Java package name.'),

     body('service.databaseType')
     .exists()
     .withMessage('Database Type is required')
     .isIn(['mysql','postgresql','mariadb'])
     .withMessage('Database Type does contain invalid value'),

     body('service.dbMigrationTool')
     .exists()
     .withMessage('DB Migration Tool is required')
     .isIn(['none','flywaydb','liquibase'])
     .withMessage('DB Migration Tool does contain invalid value'),

     body('service.cloudFeatures.*')
     .isIn(['elk','monitoring','localstack'])
     .withMessage('cloudFeatures does contain invalid value'),

     body('service.buildTool')
     .exists()
     .withMessage('Build Tool is required')
     .isIn(['maven','gradle'])
     .withMessage('Build Tool does contain invalid value'),

     body('modules.*.moduleName')
     .exists()
     .withMessage('Module Name is required')
     .isLength({ min:2, max:50 })
     .withMessage('Name should be greator than 2 and less than 50')
     .matches(/^[a-zA-Z][a-zA-Z0-9_]{2,49}$/)
     .withMessage('The module name you have provided is not valid'),

     body('modules.*.withDomainLayer')
     .exists()
     .withMessage('withDomainLayer field is required')
     .isIn(['true','false'])
     .withMessage('withDomainLayer field does contain invalid value'),

     body('modules.*.apiBasePath')
     .exists()
     .withMessage('Base Path is required')
     .matches(/^[a-zA-Z](?:(?:[\w\d-]\/?)*[A-Za-z0-9\/])$/)
     .withMessage('The base path you have provided is not valid'),

     body('modules.*.entities.*.entityName')
     .exists()
     .withMessage('Entity Name is required')
     .matches(/^[A-Z][a-zA-Z0-9_]{2,49}$/)
     .withMessage('The entity name you have provided is not valid'),

     body('modules.*.entities.*.isAttributeClass')
     .exists()
     .withMessage('isAttributeClass field is required')
     .isIn(['true','false'])
     .withMessage('isAttributeClass field does contain invalid value'),

     body('modules.*.entities.*.fields.*.fieldName')
     .exists()
     .withMessage('Field Name is required')
     .isLength({ min:2, max:50 })
     .withMessage('Name should be greator than 3 and less than 50')
     .matches(/^[a-z_$][a-zA-Z_$0-9]*$/)
     .withMessage('The field name you have provided is not valid'),

     body('modules.*.entities.*.fields.*.fieldType')
     .exists()
     .withMessage('Field Type is required')
     .isIn(['String','int','float','UUID','Date'])
     .withMessage('The field type you have provided is not valid'),

     body('modules.*.entities.*.fields.*.validation.notEmpty')
     .optional()
     .isIn(['true','false'])
     .withMessage('Not Empty field does contain invalid value'),

     body('modules.*.entities.*.fields.*.validation.notNull')
     .optional()
     .isIn(['true','false'])
     .withMessage('Not Null field does contain invalid value'),

     body('modules.*.entities.*.fields.*.validation.regExp')
     .custom(value =>{
      try {
        new RegExp(value);
        return value;
    } catch(e) {
        return Promise.reject('Invalid Regular Expression');
    }})
    .optional({ nullable: true }),

     body('modules.*.entities.*.fields.*.validation.isEmail')
     .optional()
     .isIn(['true','false'])
     .withMessage('Is Email field does contain invalid value'),

     body('modules.*.entities.*.fields.*.validation.isValid')
     .optional()
     .isIn(['true','false'])
     .withMessage('Is Valid field does contain invalid value'),

     check('modules.*.entities.*.fields.*.validation.length')
     .isInt({ min: 0, max: 200 })
     .withMessage('Length should be numeric and with in range of 0 to 200')
     .optional({ nullable: true }),

     body('modules.*.entities.*.relationships.*.otherEntityName')
     .exists()
     .withMessage('Other Entity Name is required')
     .matches(/^[A-Z][a-zA-Z0-9_]{2,49}$/)
     .withMessage('The Other Entity Name you have provided is not valid'),

     body('modules.*.entities.*.relationships.*.otherEntityRelationshipName')
     .exists()
     .withMessage('Other Entity Relationship Name is required')
     .matches(/^[A-Z][a-zA-Z0-9_]{2,49}$/)
     .withMessage('The Other Entity Relationship Name you have provided is not valid'),

     body('modules.*.entities.*.relationships.*.relationshipName')
     .exists()
     .withMessage('Relationship Name is required')
     .isLength({ min:2, max:50 })
     .withMessage('Name should be greator than 3 and less than 50')
     .matches(/^[a-z_$][a-zA-Z_$0-9]*$/)
     .withMessage('The relationship name you have provided is not valid'),

     body('modules.*.entities.*.relationships.*.relationshipType')
     .exists()
     .withMessage('Relationship Type is required')
     .isIn(['one-to-one','one-to-many','many-to-one','many-to-many'])
     .withMessage('Relationship Type does contain invalid value'),

     body('modules.*.entities.*.relationships.*.validation.notEmpty')
     .optional()
     .isIn(['true','false'])
     .withMessage('Not Empty field does contain invalid value'),

     body('modules.*.entities.*.relationships.*.validation.notNull')
     .optional()
     .isIn(['true','false'])
     .withMessage('Not Null field does contain invalid value'),

     body('modules.*.entities.*.relationships.*.validation.isEmail')
     .optional()
     .isIn(['true','false'])
     .withMessage('Is Email field does contain invalid value'),

     body('modules.*.entities.*.relationships.*.validation.isValid')
     .optional()
     .isIn(['true','false'])
     .withMessage('Is Valid field does contain invalid value'),

     (req,res)=>{
      const errors = validationResult(req);
      if (!errors.isEmpty()) {
        return res.status(400).json({ errors: errors.array() });
      }

      const SERVICE = req.body.service;
      const MODULES =req.body.modules;
      SERVICE.packageFolder = SERVICE.packageName.replace(/\./g, '/');
      let metadata = projectInfo(MODULES);
      SERVICE.metadata = metadata;
      //NOTE: SERVICE GENERATION
      const env = yeoman.createEnv([],{cwd: 'C://Aman//generated-projects'});
      env.register(require.resolve('../generators/app/index'), 'npm:app');
      env.run("npm:app", {skipInstall: true,metadata, configOptions: SERVICE}).then(result => {
      runResult = result;
      });

      injectClasses(MODULES,SERVICE);
      res.json({SERVICE,MODULES});

      });

       async function sleep(msec) {
        return new Promise(resolve => setTimeout(resolve, msec));
      }

      async function injectClasses(MODULES,SERVICE) {
      
        let metadata = projectInfo(MODULES);
        for (const MODULE of MODULES){
        if(MODULE.withDomainLayer===false){
        await sleep(3000);
        const moduleName = MODULE.moduleName;
        const withDomainLayer =MODULE.withDomainLayer;
        const apiBasePath = MODULE.apiBasePath;
        var entitiesMetaData=[];
        for (var module of metadata.modules){
          if(module.moduleName === moduleName){
           entitiesMetaData = module.entities;
          }
        }
        for (const ENTITY of MODULE.entities){
        const entityName = ENTITY.entityName;
        ENTITY.moduleName= moduleName;
        ENTITY.withDomainLayer = withDomainLayer;
        if(ENTITY.isAttributeClass===false){
          if(ENTITY.apiBasePath)
          apiBasePath=ENTITY.apiBasePath;
        }  
        const env = yeoman.createEnv([],{cwd: 'C://Aman//generated-projects//'+SERVICE.appName+'//'+SERVICE.serviceName});
        env.register(require.resolve('../generators/controller/index'), 'npm:controller');
        env.run("npm:controller", {skipInstall: true,entityName,apiBasePath,entitiesMetaData,configOptions: ENTITY }).then(result => {
         runResult = result;
         });

         for (const entityMetaData of entitiesMetaData){ 
          if(entityMetaData.className === entityName)
           entityMetaData.created=true;
         }
        }
      }else{
        const aggRoots= MODULE.aggregateRoot;
        var ImportDtos = MODULE.importModuleDtos;
        var OrmEntities = MODULE.ormEntities;
        var generateOnlyImportDtos = true;
        var generateOnlyOrmLayer = true;
        var generateAggregateRoot = true;
        var generateOnlyValueObject =true;
        var apiBasePath = MODULE.apiBasePath;
        // dtos from other modules getting generated 
        for(var dto of ImportDtos){
          await sleep(2000);
          MODULE.importModuleDtos = dto;
          //Dtos of other modules generation
          const env = yeoman.createEnv([],{cwd: 'C://Aman//generated-projects//'+SERVICE.appName+'//'+SERVICE.serviceName});
          env.register(require.resolve('../generators/controller/index'), 'npm:controller');
          env.run("npm:controller", {skipInstall: true,generateOnlyImportDtos,configOptions: MODULE }).then(result => {
           runResult = result;
           });
          }
         
        for(var aggregateRoot of aggRoots){
          await sleep(2000);
        MODULE.aggregateRoot = aggregateRoot;
        var entityName= aggregateRoot.name;
        MODULE.importModuleDtos = [...ImportDtos];
        //END TO END MODULE GENERATION
        const env = yeoman.createEnv([],{cwd: 'C://Aman//generated-projects//'+SERVICE.appName+'//'+SERVICE.serviceName});
        env.register(require.resolve('../generators/controller/index'), 'npm:controller');
        env.run("npm:controller", {skipInstall: true,generateAggregateRoot,entityName,apiBasePath,configOptions: MODULE }).then(result => {
         runResult = result;
         });
        }

        for(var aggregateRoot of aggRoots){
          MODULE.aggregateRoot = aggregateRoot;
          var entityName= aggregateRoot.name;
          for(var field of aggregateRoot.fields){
            //Value Object Generation
             if(typeof field.isValueObject !=='undefined' && field.isValueObject === true ){
              await sleep(500);
              MODULE.valueObject = field;
              const env = yeoman.createEnv([],{cwd: 'C://Aman//generated-projects//'+SERVICE.appName+'//'+SERVICE.serviceName});
              env.register(require.resolve('../generators/controller/index'), 'npm:controller');
              env.run("npm:controller", {skipInstall: true,generateOnlyValueObject,entityName,apiBasePath,configOptions: MODULE }).then(result => {
               runResult = result;
               });
             }
          }
        }

 
        for(var ormEntity of OrmEntities){
          await sleep(1000);
          MODULE.ormEntities = ormEntity;
          //Infra Layer Generation
          const env = yeoman.createEnv([],{cwd: 'C://Aman//generated-projects//'+SERVICE.appName+'//'+SERVICE.serviceName});
          env.register(require.resolve('../generators/controller/index'), 'npm:controller');
          env.run("npm:controller", {skipInstall: true,generateOnlyOrmLayer,configOptions: MODULE }).then(result => {
           runResult = result;
           });
          }
      }}
     
      }

      function projectInfo(MODULES){
        let modulelist=[];
        for (const module of MODULES){
          if(module.withDomainLayer===false){
          let entitylist=[];
          for (const entity of module.entities){ 
            let entityobject ={
                className: entity.entityName,
                attributeClass: entity.isAttributeClass,
                created: false
              };
              entitylist.push(entityobject);
          }
          let moduleobject = {
            moduleName: module.moduleName,
            entities: entitylist
          }
          modulelist.push(moduleobject);
        }}

        let metadata = {
          modules : modulelist
        }
        return metadata;
      }


     app.listen(7081);
     console.log('7081 is the magic port');


       
        /*
          var metadata = {
            modules :[
            { 
              moduleName: "Framework",
              withDomainLayer: false,
              entities:[
              {
                className: "Framework",
                attributeClass: false,
                created: false
              },
              {
                className: "Type",
                attributeClass: false,
                created: false
              },
              {
                className: "Category",
                attributeClass: false,
                created: false,
              },
              {
                className: "SoftwareType",
                attributeClass: false,
                created: false,
              },
              {
                className: "LicenseType",
                attributeClass: false,
                created: false,
              },
              {
                className: "DevelopedInLanguage",
                attributeClass: false,
                created: false,
              },
              {
                className: "SecurityReport",
                attributeClass: false,
                created: false,
              }
            ]
          },
          {
            "moduleName": "UserAccessDetails",
            "withDomainLayer": true
          }
        ]
          };
        */
          /*
        let modulelist=[];
        for (const module of MODULES){
          let entitylist=[];
          for (const entity of module.entities){ 
            let entityobject ={
                className: entity.entityName,
                attributeClass: entity.isAttributeClass,
                created: false
              };
              entitylist.push(entityobject);
          }
          let moduleobject = {
            moduleName: module.moduleName,
            entities: entitylist
          }
          modulelist.push(moduleobject);
        }

        let metadata = {
          modules : modulelist
        }
      */

     /*
     {
          moduleName: 'Employee',
          withDomainLayer: false,
          apiBasePath: 'example/v1/employee',
          entities: [
            {
              entityName: 'Employee',
              apiBasePath: 'example/v1/employee',
              withDomainLayer: false,
              isAttributeClass: false,
              fields:[
                 {
                  fieldName: 'first_name',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^[a-zA-Z ]*$',
                    length: 50,
                    isEmail: false,
                  }
                 },
                 {
                  fieldName: 'last_name',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^[a-zA-Z ]*$',
                    length: 50,
                    isEmail: false,
                  }
                 },
                 {
                  fieldName: 'email',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$',
                    length: 50,
                    isEmail: true,
                  }
                },
              ],
              relationships: [
                 {
                   otherEntityName: "Job",
                   otherEntityRelationshipName: "Employee",
                   ownerSide: true,
                   relationshipName: "job",
                   relationshipType: "one-to-many",
                   validation: {
                    notEmpty: false,
                    notNull: true,
                    isEmail: false,
                    isValid: true,
                  }
                 },
                 {
                  otherEntityName: "Department",
                  otherEntityRelationshipName: "Employee",
                  ownerSide: true,
                  relationshipName: "department",
                  relationshipType: "many-to-one",
                  validation: {
                   notEmpty: false,
                   notNull: true,
                   isEmail: false,
                   isValid: true,
                 }
                },
                {
                  otherEntityName: "Employee",
                  otherEntityRelationshipName: "Employee",
                  ownerSide: true,
                  relationshipName: "manager",
                  relationshipType: "many-to-one",
                  validation: {
                   notEmpty: false,
                   notNull: true,
                   isEmail: false,
                   isValid: true,
                 }
                }
               ],
             },
             {
              entityName: 'Job',
              apiBasePath: 'example/v1/employee',
              withDomainLayer: false,
              isAttributeClass: true,
              fields:[
                 {
                  fieldName: 'job_title',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^[a-zA-Z ]*$',
                    length: 50,
                    isEmail: false,
                  }
                 },
                 {
                  fieldName: 'salary',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^[a-zA-Z ]*$',
                    length: 50,
                    isEmail: false,
                  }
                 }
              ],
              relationships: [],
             },
             {
              entityName: 'Department',
              apiBasePath: 'example/v1/employee',
              withDomainLayer: false,
              isAttributeClass: true,
              fields:[
                 {
                  fieldName: 'dept_name',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^[a-zA-Z ]*$',
                    length: 50,
                    isEmail: false,
                  }
                 },
                 {
                  fieldName: 'location',
                  fieldType: 'String',
                  validation: {
                    notEmpty: true,
                    regExp: '^[a-zA-Z ]*$',
                    length: 50,
                    isEmail: false,
                  }
                 }
              ],
              relationships: [],
             }
          ],
        }

        */

        /*
        const ENTITIES = [
          {
            entityName: 'User',
            apiBasePath: 'example/v1/user',
            withDomainLayer: false,
            fields:[
               {
                   fieldName: 'first_name',
                   fieldType: 'String'
               },
               {
                fieldName: 'last_name',
                fieldType: 'String'
               },
               {
                fieldName: 'email',
                fieldType: 'String'
              },
            ],
            relationships: [
               {
                 otherEntityName: "Address",
                 otherEntityRelationshipName: "User",
                 ownerSide: true,
                 relationshipName: "address",
                 relationshipType: "one-to-many"
               }
             ],
           },
           {
            entityName: 'Address',
            apiBasePath: 'example/v1/user',
            withDomainLayer: false,
            isAttribute: true,
            fields:[
               {
                   fieldName: 'first_name',
                   fieldType: 'String'
               },
               {
                fieldName: 'last_name',
                fieldType: 'String'
               },
               {
                fieldName: 'email',
                fieldType: 'String'
              },
            ],
            relationships: [
               {
                 otherEntityName: "Address",
                 otherEntityRelationshipName: "User",
                 ownerSide: true,
                 relationshipName: "address",
                 relationshipType: "one-to-many"
               }
             ],
           }
        ];
        /*
    const ENTITIES = [
        {
            entityName: 'User',
            apiBasePath: 'example/v1/user',
            withDomainLayer: false,
            fields:[
               {
                   fieldName: 'first_name',
                   fieldType: 'String'
               },
               {
                fieldName: 'last_name',
                fieldType: 'String'
               },
               {
                fieldName: 'email',
                fieldType: 'String'
              },
            ],
            relationships: [
               {
                 otherEntityName: "Address",
                 otherEntityRelationshipName: "User",
                 ownerSide: true,
                 relationshipName: "address",
                 relationshipType: "one-to-many"
               }
             ],
           },
           {
            entityName: 'Region',
            apiBasePath: '/api/regions',
            withDomainLayer: false,
            fields:[
               {
                   fieldName: 'regionName',
                   fieldType: 'String'
               },
            ],
            relationships: []
           },
           {
            entityName: 'Employee',
            apiBasePath: '/api/employees',
            withDomainLayer: false,
            fields:[
               {
                   fieldName: 'employeeName',
                   fieldType: 'String'
               },
            ],
            relationships : [
              {
                otherEntityName: "Job",
                otherEntityRelationshipName: "employee",
                relationshipName: "job",
                relationshipType: "one-to-many"
              },
              {
                otherEntityName: "Employee",
                otherEntityRelationshipName: "employee",
                relationshipName: "manager",
                relationshipType: "many-to-one"
              },
              {
                otherEntityName: "Department",
                otherEntityRelationshipName: "employee",
                relationshipName: "department",
                relationshipType: "many-to-one"
              }
            ]
           }
    ];
 
    for (const ENTITY of ENTITIES){
    const entityName = ENTITY.entityName;
    const apiBasePath = ENTITY.apiBasePath;   
    const env = yeoman.createEnv([],{cwd: 'C://Aman//generated-projects//fulkrum'});
    env.register(require.resolve('../generators/controller/index'), 'npm:controller');
    env.run("npm:controller", {skipInstall: true,entityName,apiBasePath,configOptions: ENTITY }).then(result => {
     runResult = result;
     });
    }
      */