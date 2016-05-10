package unit

import globit.sandbox.person.Person
import globit.sandbox.person.PersonStorage
import spock.lang.*

class PersonStorageTesting extends Specification {

    def "saving a person in storage should set it's id"(){
        given:
        Person person = new Person(firstname:"myfirstname", lastname:"mysecondname",email:"sabboud@globit.com")
        PersonStorage ps = new PersonStorage()

        when:
        ps.save(person)

        then:
        person.id != null
    }

    def "saved persons should have unique ids"(){
        given:
        Person person1 = new Person(firstname:"myfirstname1", lastname:"mysecondname1",email:"sabboud@globit.com")
        Person person2 = new Person(firstname:"myfirstname2", lastname:"mysecondname2",email:"sabboud@globit.com")
        PersonStorage ps = new PersonStorage()

        when:
        ps.save(person1)
        ps.save(person2)

        then:
        person1.id != person2.id
    }

    def "saving a person in storage and then delete it then the created id should not exist"(){
        given:
        Person person = new Person(firstname:"myfirstname", lastname:"mysecondname",email:"sabboud@globit.com")
        PersonStorage ps = new PersonStorage()

        when:
        ps.save(person)
        ps.delete(person.id)

        then:
        person.id == null
    }

    def "saving a person in storage and then find it. The id of the found person should be the same entered id with find"(){
        given:
        Person person = new Person(firstname:"myfirstname", lastname:"mysecondname",email:"sabboud@globit.com")
        PersonStorage ps = new PersonStorage()

        when:
        ps.save(person)
        Person tempperson = ps.find(person.id)

        then:
        person.id == tempperson.id
    }
}

