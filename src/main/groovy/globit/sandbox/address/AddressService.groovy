package globit.sandbox.address

class AddressService {
    AddressStorage addressStorage = new AddressStorage()
    Address find(String id){
        addressStorage.find(id)
    }
    Map findAll(){
        addressStorage.findAll()
    }
    void delete(String id){
        addressStorage.delete(id)
    }
    void delete(Address address){
        addressStorage.delete(address)
    }
    void save(Address address){
        if (validate(address)){
            addressStorage.save(address)
        }
    }

    static boolean validate(Address address){
        Boolean letter = false
        char[] chars = address.zipcode.toCharArray();
        for (char c : chars){
            if(Character.isLetter(c)) letter = true
        }
        if (address.zipcode.length()!= 5 || letter){
            return false
        }
        else  true

    }
}
