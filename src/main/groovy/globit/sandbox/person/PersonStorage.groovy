package globit.sandbox.person

class PersonStorage {
    Map personMap = [:]

    String generateid(){

        UUID.randomUUID()
    }
     void save(Person person){
         if (!person.id) {
             person.id = generateid()
         }
         personMap.put(person.id, person)
     }

    void delete(Person person){
        if(person.id){
            personMap.remove(person.id,person)
            person.id = null
        }
    }

    void delete(String id){
        if (personMap.containsKey(id)){
            Person person = personMap.get(id)
            personMap.remove(id,person)
            person.id = null

        }

    }
    Map findAll(){
        return personMap
    }

    Person find(String id){
        personMap.get(id)
    }

}
