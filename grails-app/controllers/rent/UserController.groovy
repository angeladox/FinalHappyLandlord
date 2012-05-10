package rent

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

class UserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def springSecurityService
   
	def currentUser() {
		def user = User.get(springSecurityService.principal.id)
	 }
 
 @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
	 def edited= {
		 
		 if (!params.submit) {
			 
			 return [user: currentUser()]
		 }
		 def user = currentUser()
		 String one = params.password
		 String two = params.passwordrepeat
		
		 if(one != two){
			 user.password = ""
			 flash.message = "edit.invalid.message"
			 return [user: currentUser()]
		 }
		 
		 
		  user.properties = params
		
		 if(user.validate()){
			 user.save()
			 redirect(controller: "login", action: "auth")
		 }else{
			 user.password = ""
			 return [user: currentUser()]
		 }
	 }
 
	 
    def register= {
        if (!params.register) return
        
        if (params) {
            
            def user = new User(
            username: params.username,
            firstName: params.firstName, 
            lastName: params.lastName,
            password: params.password,
            enabled: true,
            dateCreated: new Date(),
            email: params.email,
            phone: params.phone
            )
                                     
            if (user.validate()) {
                user.save()
                flash.message = "Successfully Created User"
                
                sendMail {
                    to params.email /// to where it should go. we should get the email from param
                    from "Qyaqub@happLandLord.com" ///  not really cool
                    subject "Welcome to happy landlord!" 
                    html '<table> <tr> <td > Dear' + " ${params.firstName}:" + 
                    ' </td></tr><tr><td> <br><p>Thank you for registering with Happy Landlord! :)<br><br><br> The Happy Landlord Team - ABQ - </p> </td></tr></table> '
                    
                }
                ////////////////////
                def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)                
                
                if (!user.authorities.contains(userRole)) {
                    SecUserSecRole.create user, userRole
                }
                              
                /////////////////////
                
                redirect (controller: 'login', action: 'index' ) 
            }else{
                flash.message = "Error Registering User"
                [ user: user ] 
            }

        }else{
            [user: user]
        }  
    }
     
    def index() {
        redirect(action: "list", params: params)
    }

	@Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

	@Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
    def create() {
        [userInstance: new User(params)]
    }

	@Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
    def save() {
        def userInstance = new User(params)
        if (!userInstance.save(flush: true)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def show() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

	@Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
    def edit() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

	@Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
    def update() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'user.label', default: 'User')] as Object[],
                          "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
        }

        userInstance.properties = params

        if (!userInstance.save(flush: true)) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

	@Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
    def delete() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        try {
            userInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
