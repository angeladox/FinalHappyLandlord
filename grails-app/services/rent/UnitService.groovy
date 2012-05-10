package rent

class UnitService {

	def getRandomUnit() {
		def allUnits = Unit.findByStatus("Available")
		if (allUnits.size()==0){
			return new Unit(unitNo:'g',
				sqFeet: '1200',
				//byte[] photo
				description: 'a pretty sweet unit, yo',
				bedrooms:'9',
				status: 'Available'
				)
		}
		else {
			def randomUnit = null
			if (allUnits.size() > 0) {
				def randomIdx = new Random().nextInt(allUnits.size())
				randomUnit = allUnits[randomIdx]
			} else {
				randomUnit = getRandomUnit()
			}
			return randomUnit
		}
	}

}
