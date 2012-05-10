package rent

class TenantService {

	def springSecurityService

	def currentUser() {
		def user = User.get(springSecurityService.principal.id)
	}

	def landlord = currentUser()
	def getList = {
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
