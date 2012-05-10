package rent

import java.util.Date;

import grails.converters.XML

class TenantRestController {

	def list = { render Tenant.list() as XML }

	def show = {
		def ten = Tenant.get(params.id)
		render ten as XML
	}

	def save = {
		def xml = request.XML
		def ten = new Tenant(firstName: xml.address.text(),
				lastName: xml.lastName.text(),
				email: xml.email.text(),
				phone: xml.phone.text(),
				creditScore: xml.phone.text(),
				prevAddress: xml.prevAddress.text(),
				prevLandlord: xml.prevLandlord.text(),
				prevLandlordPhone: xml.prevLandlordPhone.text(),
				comments: xml.comments.text(),
				dateCreated: xml.dateCreated.text())

		def markup
		if (ten.save()) {
			markup = {
				status("OK")
			}
		}
		else {
			markup = {
				status("YOU FAILED.")
			}
		}
		render contentType: "text/xml; charset=utf-8",
				markup
	}
}
