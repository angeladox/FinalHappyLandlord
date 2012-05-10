package rent

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

class UnitController {

	def springSecurityService
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "unitList", params: params)
	}

	@Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[unitInstanceList: Unit.list(params), unitInstanceTotal: Unit.count()]
	}

	@Secured(['ROLE_USER'])
	def create() {
		[unitInstance: new Unit(params)]
	}

	@Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
	def save() {
		def unitInstance = new Unit(params)
		if (!unitInstance.save(flush: true)) {
			render(view: "create", model: [unitInstance: unitInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'unit.label', default: 'Unit'), unitInstance.id])
		redirect(action: "show", id: unitInstance.id)
	}

	def show() {
		def unitInstance = Unit.get(params.id)
		if (!unitInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'unit.label', default: 'Unit'), params.id])
			redirect(action: "unitList")
			return
		}

		[unitInstance: unitInstance]
	}

	@Secured(['ROLE_USER'])
	def edit() {
		def unitInstance = Unit.get(params.id)
		if (!unitInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'unit.label', default: 'Unit'), params.id])
			redirect(action: "unitList")
			return
		}

		[unitInstance: unitInstance]
	}

	@Secured(['ROLE_USER'])
	def update() {
		def unitInstance = Unit.get(params.id)
		if (!unitInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'unit.label', default: 'Unit'), params.id])
			redirect(action: "unitList")
			return
		}

		if (params.version) {
			def version = params.version.toLong()
			if (unitInstance.version > version) {
				unitInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[message(code: 'unit.label', default: 'Unit')] as Object[],
						"Another user has updated this Unit while you were editing")
				render(view: "edit", model: [unitInstance: unitInstance])
				return
			}
		}

		unitInstance.properties = params

		if (!unitInstance.save(flush: true)) {
			render(view: "edit", model: [unitInstance: unitInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'unit.label', default: 'Unit'), unitInstance.id])
		redirect(action: "show", id: unitInstance.id)
	}

	@Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
	def delete() {
		def unitInstance = Unit.get(params.id)
		if (!unitInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'unit.label', default: 'Unit'), params.id])
			redirect(action: "unitList")
			return
		}

		try {
			unitInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'unit.label', default: 'Unit'), params.id])
			redirect(action: "unitList")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'unit.label', default: 'Unit'), params.id])
			redirect(action: "show", id: params.id)
		}
	}

	def currentUser() {
		def user = User.get(springSecurityService.principal.id)
	 }
	
	
	def unitList = {
		def landlord = currentUser()
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		ArrayList<Property> props = new ArrayList<Property>()
		for (Property prop: landlord.asset)
			props.push(prop)
		ArrayList<Unit> uns = new ArrayList<Unit>()
		for (Property prop: props){
			for (Unit unit: prop.units)
				uns.push(unit)
		}
		
		[landlord: landlord, unitAmt: uns.size(), units: uns]
	}

}
