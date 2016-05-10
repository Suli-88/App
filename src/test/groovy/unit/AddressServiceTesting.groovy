package unit

import globit.sandbox.address.Address
import globit.sandbox.address.AddressService
import globit.sandbox.address.AddressStorage
import spock.lang.*
class AddressServiceTesting extends  Specification{



    def "saving a person in storage should set it's id"(){
        given:
        Address person = new Address(zipcode:"22885")
        AddressService ad = new AddressService()

        when:
        ad.save(person)

        then:
        person.id != null
    }

    def "saved persons should have unique ids"(){
        given:
        Address address1 = new Address(zipcode:"22885")
        Address address2 = new Address(zipcode:"22885")
        AddressService ps = new AddressService()

        when:
        ps.save(address1)
        ps.save(address2)

        then:
        address1.id != address2.id
    }

    def "saving a person in storage and then delete it then the created id should not exist"(){
        given:
        Address address = new Address(zipcode:"22885")
        AddressService ps = new AddressService()

        when:
        ps.save(address)
        ps.delete(address.id)

        then:
        address.id == null
    }

    def "saving a person in storage and then find it. The id of the found person should be the same entered id with find"(){
        given:
        Address address = new Address(zipcode:"22885")
        AddressService ps = new AddressService()

        when:
        ps.save(address)
        Address temmaddress = ps.find(address.id)

        then:
        address.id == temmaddress.id
    }

    def "savine an with an unvalid zip code"(){
        given:
        Address address = new Address(zipcode:"222t5")
        AddressService ps = new AddressService()

        when:
        ps.save(address)

        then:
        address.id == null
    }

}
