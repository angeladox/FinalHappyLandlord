package rent
import grails.converters.XML

class UnitRestController {

  def list = { render Unit.list() as XML }

	def show = {
		def prop = Unit.get(params.id)
		render prop as XML
	}

	def save = {
		def xml = request.XML
		
		def unit = new Unit(unitNo: xml.unitNo.text(),
		sqFeet: xml.sqFeet.text(),
		description: xml.description.text(),
		bedrooms : xml.bedrooms.text(),
		status : xml.status.text())
			
		def markup
		if (unit) {
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
