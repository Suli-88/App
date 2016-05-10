package globit.sandbox.Errors
import globit.sandbox.address.Address
import globit.sandbox.address.AddressService
import globit.sandbox.address.AddressStorage

public class ValidationError extends Exception {

    String what
    String where
    String expect
    public ValidationError(String where, String what,String expect){
        //def message = "not valid $what, because $where, expected $expect"
        super("not valid $where, because $what, expected $expect")
        this.what = what
        this.where = where
        this.expect = expect
    }
    /*
    public ValidationError(List<String> error){
        Iterator<String> iter = error.iterator();
        while (iter.hasNext()){
            def message = iter.next()
            super(message)
        }
        //def message = "not valid $what, because $where, expected $expect"
        super("not valid $where, because $what, expected $expect")
        this.what = what
        this.where = where
        this.expect = expect
    }

    */
}
