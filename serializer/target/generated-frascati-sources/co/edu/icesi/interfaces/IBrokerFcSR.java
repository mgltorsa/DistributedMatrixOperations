/*
 * Generated by: org.ow2.frascati.tinfi.opt.oo.ServiceReferenceClassGenerator
 * on: Mon Jun 03 20:26:22 COT 2019
 */

package co.edu.icesi.interfaces;


public class IBrokerFcSR
extends org.ow2.frascati.tinfi.oasis.ServiceReferenceImpl<co.edu.icesi.interfaces.IBroker>
implements co.edu.icesi.interfaces.IBroker {

  public IBrokerFcSR(Class<co.edu.icesi.interfaces.IBroker> businessInterface,co.edu.icesi.interfaces.IBroker service)  {
    super(businessInterface,service);
  }

  public co.edu.icesi.interfaces.IBroker getService()  {
    return this;
  }

  public java.lang.String[] getTiffProcessors(final int arg0) throws java.lang.IllegalArgumentException  {
    java.lang.String[] ret = service.getTiffProcessors(arg0);
    return ret;
  }

  public void register(final java.lang.String arg0,final int arg1,final java.lang.String arg2) throws java.lang.IllegalArgumentException  {
    service.register(arg0,arg1,arg2);
  }

  public int getTotalProcessors()  {
    int ret = service.getTotalProcessors();
    return ret;
  }

  public java.lang.String[] getImageSerializers() throws java.lang.IllegalArgumentException  {
    java.lang.String[] ret = service.getImageSerializers();
    return ret;
  }

}
