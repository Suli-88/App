package unit

import globit.sandbox.address.Address
import globit.sandbox.address.AddressStorage
import spock.lang.*
class AddressStorageTesting extends Specification {


    def "saving a address in storage should set it's id"(){
        given:
        Address address = new Address(zipcode:"22885")
        AddressStorage addressStorage = new AddressStorage()

        when:
        addressStorage.save(address)

        then:
        address.id != null
    }

    def "saved addresses should have unique ids"(){
        given:
        Address address1 = new Address(zipcode:"22885")
        Address address2 = new Address(zipcode:"22885")
        AddressStorage ps = new AddressStorage()

        when:
        ps.save(address1)
        ps.save(address2)

        then:
        address1.id != address2.id
    }

    def "saving an address in storage and then delete it then the created id should not exist"(){
        given:
        Address address = new Address(zipcode:"22885")
        AddressStorage ps = new AddressStorage()

        when:
        ps.save(address)
        ps.delete(address.id)

        then:
        address.id == null
    }

    def "saving an address in storage and then find it. The id of the found person should be the same entered id with find"(){
        given:
        Address address = new Address(zipcode:"22885")
        AddressStorage ps = new AddressStorage()

        when:
        ps.save(address)
        Address tempaddress = ps.find(address.id)

        then:
        address.id == tempaddress.id
    }
}
