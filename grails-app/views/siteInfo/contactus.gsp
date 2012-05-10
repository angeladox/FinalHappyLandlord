<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
     <meta name="layout" content="main"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Contact us</title>
  </head>
  <body>
    <h1> Contact Us</h1>
    
    <g:form controller="siteInfo" action="contactus" style ="font-family: tahoma; font-size: .9em;
                margin-left: 19em;">
            <dl>
                <dt>Your email Address</dt>
                <dd>
                    <g:textField name="email" value="${siteinfo?.email}"/>
                    <g:hasErrors bean="${siteinfo}" field="email">
                        <g:eachError bean="${siteinfo}" field="email">
                            <p style="color: red;"><g:message error="${it}"/></p>
                        </g:eachError>
                    </g:hasErrors>
                </dd>
                
                <dt>Your Name</dt>
                <dd><g:textField name="name" value="${siteinfo?.name}"/>
                <g:hasErrors bean="${siteinfo}" field="name">
                        <g:eachError bean="${siteinfo}" field="name">
                            <p style="color: red;"><g:message error="${it}"/></p>
                        </g:eachError>
                    </g:hasErrors>
                </dd>
                
                <dt>Question/Comments</dt>               
                <dd><g:textArea name="comments" value="${siteinfo?.comments}"/>
                <g:hasErrors bean="${siteinfo}" field="comments">
                        <g:eachError bean="${siteinfo}" field="comments">
                            <p style="color: red;"><g:message error="${it}"/></p>
                        </g:eachError>
                    </g:hasErrors>
                </dd>
                
                
                <br> 
                <dt><g:submitButton name="contactus" value="Submit" style="font-family: tahoma;"/></dt>
            </dl>

        </g:form>
  </body>
</html>
