package globit.sandbox.person

import globit.sandbox.Errors.ValidationError


class PersonService {
    PersonStorage personStorage = new PersonStorage()
    Person find(String id){
        personStorage.find(id)
    }
     Map findAll(){
         personStorage.findAll()
     }
    void delete(String id){
        personStorage.delete(id)
    }
    void delete(Person person){
        personStorage.delete(person)
    }
    void save(Person person){
        //def errors = validate(person)
        if (validate(person)){
            personStorage.save(person)
        }
    }
    /*
    void save(Person person){
        def errors = validate(person)
        if (errors){
            throw new ValidationError(errors)
        }
        personStorage.save(person)
    }
    */
    static boolean validate(Person person){
        //List errors = []
        if (!person.firstname)
            throw new ValidationError("person.firstname","is blank","not blank")
           // errors << ["person.firstname","is blank","not blank"]

        if(person.firstname.length()>16)
            throw new ValidationError("person.firstname","too long","must not contain more than 16 chars")

        if (person.lastname.length()==0)
            throw new ValidationError("person.lastname","is blank","not blank")

        if (person.lastname.length()>=16){
            throw new ValidationError("person.lastname","too long","must not contain more than 16 chars")
        }

        if (!person.email){
            throw new ValidationError("person.email","no email is found","Every person should have an email")
        }

        if (person.email.length()>=30){
            throw new ValidationError("person.email","the email is too long","the email muss not contain more the 16 chars")
        }

        if (!person.email.contains('@')){
            throw new ValidationError("person.email", "@ missing", "emails should have @ char")
        }

        return true
        //return errors
    }
}
