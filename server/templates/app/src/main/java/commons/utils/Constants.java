package <%= packageName %>.commons.utils;
<%_ const entityNameUpperCase = function(str){
    return str.toUpperCase();
} _%>  

public class Constants {

        public static final String DUPLICATE_ENTRY="CIAM 105";
        public static final String REQUIRED_FIELD_MISSING="CIAM 102";
        public static final String INVALID_EMAIL_ID = "CIAM 201";
        public static final String INVALID_FILED_LENGTH="CIAM 104";
        public static final String INVALID_FIELD_VALUE= "CIAM 115";
        public static final String EMPTY_REQUEST_BODY="CIAM 110";
        public static final String INTERNAL_SERVER_ERROR="CIAM 101";
      
        <%_  for(let module of metadata.modules){ _%>
         //==============================<%= entityNameUpperCase(module.moduleName) %>
        <%_  for(let entity of module.entities){ _%>
        <%_  if(entity.attributeClass===false){ _%>
        public static final String <%= entityNameUpperCase(entity.className) %>_NOT_FOUND="CIAM 200";
        public static final String <%= entityNameUpperCase(entity.className) %>_ADDED_SUCCESSFULLY="CIAM 251";
        public static final String <%= entityNameUpperCase(entity.className) %>_EDITED_SUCCESSFULLY="CIAM 252";
        public static final String <%= entityNameUpperCase(entity.className) %>_DELETED = "CIAM 255";
        public static final String <%= entityNameUpperCase(entity.className) %>_FOUND = "CIAM 250";
        <%_ }_%> 

        <%_ }_%> 

        <%_ }_%> 

       
}