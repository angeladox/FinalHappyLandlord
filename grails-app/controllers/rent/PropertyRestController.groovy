package rent
import grails.converters.XML

class PropertyRestController {

	def list = { render Property.list() as XML }

	def show = {
		def prop = Property.get(params.id)
		render prop as XML
	}

	def save = {
		def xml = request.XML
		def property = new Property(address: xml.address.text(),
		city: xml.city.text(),
		state: xml.state.text(),
		zipCode : xml.zipCode.text(),
		heating : xml.heating.text())
			
		def markup
		if (property.save()) {
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
