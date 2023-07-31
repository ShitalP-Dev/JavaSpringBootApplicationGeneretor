const { objectContent } = require("yeoman-assert");

module.exports = {
    prompting
  // askForFields
};

function prompting() {

    const done = this.async();

    const prompts = [
        {
            type: 'list',
            name: 'withDomainLayer',
            message: 'Would you like to have a domain layer for this module?',
            choices: [
                {
                    value: 'yes',
                    name: 'Yes'
                },
                {
                    value: 'no',
                    name: 'No'
                }
            ],
            default: 'no'
        }
        ];

        const fieldPrompts=[
          {
          type: 'confirm',
          name: 'fieldAdd',
          message: 'Do you want to add a field to your entity?',
          default: true,
        },
      {
          when: response => response.fieldAdd === true,
          type: 'input',
          name: 'fieldName',
          message: 'Enter field name?',
          default: 'myField',
      },
      {
        when: response => response.fieldAdd === true,
        type: 'list',
        name: 'fieldType',
        message: 'Enter field type?',
        choices: [
          {
            value: 'String',
            name: 'String',
          },
          {
            value: 'Integer',
            name: 'Integer',
          }],
          default: 0,
    }];

    this.fields=[];
    //const fields=[];
    
    const loop = (relevantPrompts) => {
      return this.prompt(relevantPrompts).then(answers => {
        this.fields.push(answers); 
        return answers.fieldAdd ? loop(fieldPrompts) : this.prompt([]);  
      })
    };
      
     return loop([...prompts, ...fieldPrompts]); 

      // for(let i=0; i<this.fields.length;i++){
      //   Object.assign(this.configOptions, this.fields[i]);    
      // }

      // done();

       //Object.assign(this.configOptions, fields);
      // done();
    }






/*

module.exports = {
 askForFields,
 askForDomainLayer
};

function askForDomainLayer(){

  const prompts = [
    {
        type: 'list',
        name: 'withDomainLayer',
        message: 'Would you like to have a domain layer for this module?',
        choices: [
            {
                value: 'yes',
                name: 'Yes'
            },
            {
                value: 'no',
                name: 'No'
            }
        ],
        default: 'no'
    }
    ];
   
   return this.prompt(prompts).then(answers => {
        Object.assign(this.configOptions, answers);
    });
}

function askForFields(){

  const fieldPrompts=[
    {
    type: 'confirm',
    name: 'fieldAdd',
    message: 'Do you want to add a field to your entity?',
    default: true,
  },
{
    when: response => response.fieldAdd === true,
    type: 'input',
    name: 'fieldName',
    message: 'Enter field name?',
    default: 'myField',
},
{
  when: response => response.fieldAdd === true,
  type: 'list',
  name: 'fieldType',
  message: 'Enter field type?',
  choices: [
    {
      value: 'String',
      name: 'String',
    },
    {
      value: 'Integer',
      name: 'Integer',
    }],
    default: 0,
}];

 return this.prompt(fieldPrompts).then(answers => {
  Object.assign(this.configOptions, answers);
   if(answers.fieldAdd){
   askForFields.call(this);
   }
});
}

*/


























    
   