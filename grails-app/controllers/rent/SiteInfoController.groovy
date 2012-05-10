package rent

class SiteInfoController {

	def aboutus = {}
	def contacterror = {}
	def contactsuccess = {}
	def contactus = {
		if(!params.contactus) return
			if (params) {

				def siteinfo = new SiteInfo(
						email: params.email,
						name: params.name,
						comments: params.comments
						)

				if (siteinfo.validate()) {
					siteinfo.save()
					flash.message = "Successfully Sent Message"

					sendMail {
						to "angela.doxsey@yahoo.com"
						from params.email
						subject "New contact us message"
						html '<table> <tr> <td > Message from:  <br> </table>' + params.email + '<br> Contains the following:  <br>' + params.comments

					}

					redirect(action: 'contactsuccess')
				}
				else{
					[siteinfo: siteinfo]}
			}else{
				flash.message = "Error Sending Message"
				[siteinfo: siteinfo]
			}
	}


	def privacy = {}

	def propertyService

	def home = {
		def randomProp = propertyService.getRandomProp()
		[ prop : randomProp ]
		redirect(uri: "/", props)
	}

	def ajaxRandom = {
		def randomProp = propertyService.getRandomProp()
		 render "<p> Address: ${randomProp.address}</p>" +
				"<p> City: ${randomProp.city}</p>" +
				"<p> State: ${randomProp.state}</p>" +
				"<p> Zip Code: ${randomProp.zipCode}</p>" +
				"<p> Heating: ${randomProp.heating}</p>"
	}
	/*def proppags = {
		def propages = getAllProp()
		for (int i = 0; i < 5; i++){
		render "<p> Address: ${propages.getAt(i).address}</p>" +
				"<p> City: ${propages.getAt(i).city}</p>" +
				"<p> State: $propages.getAt(i).state}</p>" +
				"<p> Zip Code: ${propages.getAt(i).zipCode}</p>" +
				"<p> Heating: ${propages.getAt(i).heating}</p>"
				"\n"
		}		
	}*/
	
	def getAllProp(){
		def allProps = Property.list
		params.max = Math.min(params.max ? params.int('max') : 5, 100)
		//def searchby = SearchBy.findById(params.id)
		//def query = { eq('searchby', searchby)
		[propertyInstanceList: Property.list(params), propertyInstanceTotal: Property.count()]
			/*def domainInstanceList = Domain.createCriteria().list(params, query)
			def domainInstanceTotal = Domain.createCriteria().count(query)
			request.domainInstanceList = domainInstanceList
			request.domainInstanceTotal = domainInstanceTotal*/
			
		//}
	}
	
}





