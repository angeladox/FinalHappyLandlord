package rent

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

class TenantController {
	
	def springSecurityService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
    def index() {
        redirect(action: "list", params: params)
    }

	@Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tenantInstanceList: Tenant.list(params), tenantInstanceTotal: Tenant.count()]
    }

	@Secured(['ROLE_USER'])
    def create() {
        [tenantInstance: new Tenant(params)]
    }

	@Secured(['ROLE_USER'])
    def save() {
        def tenantInstance = new Tenant(params)
        if (!tenantInstance.save(flush: true)) {
            render(view: "create", model: [tenantInstance: tenantInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'tenant.label', default: 'Tenant'), tenantInstance.id])
        redirect(action: "show", id: tenantInstance.id)
    }

    def show() {
        def tenantInstance = Tenant.get(params.id)
        if (!tenantInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'tenant.label', default: 'Tenant'), params.id])
            redirect(action: "list")
            return
        }

        [tenantInstance: tenantInstance]
    }

	@Secured(['ROLE_USER'])
    def edit() {
        def tenantInstance = Tenant.get(params.id)
        if (!tenantInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tenant.label', default: 'Tenant'), params.id])
            redirect(action: "list")
            return
        }

        [tenantInstance: tenantInstance]
    }

	@Secured(['ROLE_USER'])
    def update() {
        def tenantInstance = Tenant.get(params.id)
        if (!tenantInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tenant.label', default: 'Tenant'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (tenantInstance.version > version) {
                tenantInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tenant.label', default: 'Tenant')] as Object[],
                          "Another user has updated this Tenant while you were editing")
                render(view: "edit", model: [tenantInstance: tenantInstance])
                return
            }
        }

        tenantInstance.properties = params

        if (!tenantInstance.save(flush: true)) {
            render(view: "edit", model: [tenantInstance: tenantInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'tenant.label', default: 'Tenant'), tenantInstance.id])
        redirect(action: "show", id: tenantInstance.id)
    }

	@Secured(['ROLE_USER'])
    def delete() {
        def tenantInstance = Tenant.get(params.id)
        if (!tenantInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'tenant.label', default: 'Tenant'), params.id])
            redirect(action: "list")
            return
        }

        try {
            tenantInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'tenant.label', default: 'Tenant'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tenant.label', default: 'Tenant'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	def currentUser() {
		def user = User.get(springSecurityService.principal.id)
	 }
	
	@Secured(['ROLE_USER'])
	def tenantList() {
		def landlord = currentUser()
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		ArrayList<Property> props = new ArrayList<Property>()
		for (Property prop: landlord.asset)
			props.push(prop)
		println props
		ArrayList<Unit> uns = new ArrayList<Unit>()
		for (Property prop: props){
			for (Unit unit: prop.units)
				uns.push(unit)
		}
		println uns
		def uSize = uns.size()
		ArrayList<Tenant> tens = new ArrayList<Tenant>()
		ArrayList<Tenant> uTens = new ArrayList<Tenant>()
		if (uSize){
			for (int i=0; i<uSize; i++){
				Unit un = uns.getAt(i)				
				uTens.push(un.tenants)
			}
			for (def tena: uTens)
				tens.push(tena)
			
		}
		println tens
		[landlord: landlord, tenantAmt: tens.size(), tenants: tens]
	}
}
