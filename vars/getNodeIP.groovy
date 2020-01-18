#!/usr/bin/env groovy
def call(){
    //  refference : https://github.com/hyperhq/hykins/blob/master/groovy/initJenkinsURL/setup-jenkins-script.groovy
    def found = false
    def hostname = ""
    def ip = ""
    try {
        hostname = InetAddress.localHost.canonicalHostName
        ip = InetAddress.getByName(hostname).address.collect { it & 0xFF }.join('.')
    } catch(Exception ex) {
        println("Can not get ip via hostname, now get ip from network interface");
        def interfaces = NetworkInterface.getNetworkInterfaces()
        while (interfaces.hasMoreElements() && !found) {
            def addresses = interfaces.nextElement().getInetAddresses()
            while (addresses.hasMoreElements() && !found) {
                InetAddress address = addresses.nextElement();
                if (!address.isLoopbackAddress() && address.getHostName().indexOf(".hypernetes")>0){
                    ip = address.getHostAddress()
                    hostname = address.getHostName()
                    found = true
                }
            }
        }
    }
    println ip + " => " + hostname
    return ip
}
