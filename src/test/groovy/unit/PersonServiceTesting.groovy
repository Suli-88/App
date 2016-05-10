package unit
/**
 * Created by Sulaiman on 01.12.2015.
 */

import globit.sandbox.Errors.ValidationError
import globit.sandbox.person.Person
import globit.sandbox.person.PersonService
import spock.lang.*

class PersonServiceTesting extends Specification {


    def "saving a person in storage should set it's id"(){
        given:
        Person person = new Person(firstname:"myfirstname", lastname:"mysecondname",email:"sabboud@globit.com")
        PersonService ps = new PersonService()

        when:
        ps.save(person)

        then:
        person.id != null
    }

    def "saved persons should have unique ids"(){
        given:
        Person person1 = new Person(firstname:"myfirstname1", lastname:"mysecondname1",email:"sabboud@globit.com")
        Person person2 = new Person(firstname:"myfirstname2", lastname:"mysecondname2",email:"sabboud@globit.com")
        PersonService ps = new PersonService()

        when:
        ps.save(person1)
        ps.save(person2)

        then:
        person1.id != person2.id
    }

    def "saving a person in storage and then delete it then the created id should not exist"(){
        given:
        Person person = new Person(firstname:"myfirstname", lastname:"mysecondname",email:"sabboud@globit.com")
        PersonService ps = new PersonService()

        when:
        ps.save(person)
        ps.delete(person.id)

        then:
        person.id == null
    }

    def "saving a person in storage and then find it. The id of the found person should be the same entered id with find"(){
        given:
        Person person = new Person(firstname:"myfirstname", lastname:"mysecondname",email:"sabboud@globit.com")
        PersonService ps = new PersonService()

        when:
        ps.save(person)
        Person tempperson = ps.find(person.id)

        then:
        person.id == tempperson.id
    }


    /*
    ** Validaion testing
     */
    def "saving a person with a firstname that doesn't satisfy the condition in the validation: no firstname is found error"(){
        given:
        Person person = new Person(firstname:"", lastname:"mysecondname",email:"sabboud@globit.com")
        PersonService ps = new PersonService()

        when:
        ps.save(person)

        then:
        thrown ValidationError

        //person.id == null
    }
    /*
    def "saving a person with a firstname that doesn't satisfy the condition in the validation: no firstname is found error"(){
        given:
        Person person = new Person(firstname:"", lastname:"mysecondname",email:"sabboud@globit.com")
        PersonService ps = new PersonService()

        when:
        def errors = ps.validate(person)

        then:
        ["person.firstname", "is blank", "not blank"] in errors

        //person.id == null
    }
    */
    def "savine a person with a secondname that doesn't satisfy the condition in the validation: no lastname is found error"(){
        given:
        Person person = new Person(firstname:"myfirstname", lastname:"",email:"sabboud@globit.com")
        PersonService ps = new PersonService()

        when:
        ps.save(person)

        then:
        def e = thrown ValidationError

        e.where == "person.lastname"
        e.what == "is blank"
        e.expect == "not blank"

        //person.id == null
    }

    def "savine a person with a firstname that doesn't satisfy the condition in the validation: too long firstname error"(){
        given:
        Person person = new Person(firstname:"ddddddddddddddddddddafffffffffffffffffffffffffffffffffffffffffffffffffffff", lastname:"mysecondname",email:"sabboud@globit.com")
        PersonService ps = new PersonService()

        when:
        ps.save(person)

        then:
        def e = thrown ValidationError

        e.where == "person.firstname"
        e.what == "too long"
        e.expect == "must not contain more than 16 chars"

        //person.id == null
    }

    def "saving a person with a lastname that doesn't satisfy the condition in the validation: too long secondname error"(){
        given:
        Person person = new Person(firstname:"ddd", lastname:"mysecondnddameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",email:"sabboud@globit.com")
        PersonService ps = new PersonService()

        when:
        ps.save(person)

        then:
        def e = thrown ValidationError

        e.where == "person.lastname"
        e.what == "too long"
        e.expect == "must not contain more than 16 chars"

        //person.id == null
    }

    def "savine a person with an email that doesn't satisfy the condition in the validation: no email is found error"(){
        given:
        Person person = new Person(firstname:"myfirstname", lastname:"mysecondname",email:"")
        PersonService ps = new PersonService()

        when:
        ps.save(person)

        then:

        def e = thrown ValidationError
        e.where == "person.email"
        e.what == "no email is found"
        e.expect == "Every person should have an email"

    }


    def "savine a person with an email that doesn't satisfy the condition in the validation: too long email error"(){
        given:
        Person person = new Person(firstname:"myfirstname", lastname:"mysecondname",email:"sabboudafdffffffffffffffffffffffffffsdfdsfsdd@glsdfsdsddsdssdsddssobit.com")
        PersonService ps = new PersonService()

        when:
        ps.save(person)

        then:
        def e = thrown(ValidationError)
        with(e) {
            where == "person.email"
            what == "the email is too long"
            expect == "the email muss not contain more the 16 chars"
        }
    }

    def "savine a person with an email that doesn't satisfy the condition in the validation: missing @ char error"(){
        given:
        Person person = new Person(firstname:"myfirstname", lastname:"mysecondname",email:"sabboudglobit.com")
        PersonService ps = new PersonService()

        when:
        ps.save(person)

        then:
        //person.id == null
        //thrown ValidationError
        def e = thrown ValidationError
        e.where == "person.email"
        e.what == "@ missing"
        e.expect == "emails should have @ char"

    }



}
