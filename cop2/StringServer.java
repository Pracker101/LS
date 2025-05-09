import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import StringApp.StringOperations;

public class StringServer {
    public static void main(String[] args) {
        try {
            // ORB and POA setup
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Create servant and get CORBA reference
            StringOperationsImpl impl = new StringOperationsImpl();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(impl);
            StringOperations href = StringApp.StringOperationsHelper.narrow(ref);

            // Bind to Naming Service
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            NameComponent path[] = ncRef.to_name("StringOperations");
            ncRef.rebind(path, href);

            System.out.println("Server ready...");
            orb.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
