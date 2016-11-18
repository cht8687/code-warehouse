# Maker Den Documentation and Resources

![Maker Den](https://raw.githubusercontent.com/MakerDen/IoT-Maker-Den-Documentation-and-Resources/master/Resources/Images/MakerDen.jpg)

#Iot Den Source Code
Source code for the [Maker Den on Windows 10 IoT Core](https://github.com/MakerDen/IoT-Maker-Den-Windows-for-IoT) project. 

##WINDOWS 10 IOT CORE
The lab has a dependency on Windows 10 IoT Core version 10.0.14342 and above. At at July, 2016, this is the [Insiders Preview](http://ms-iot.github.io/content/en-US/Downloads.htm).


##Device Hardware and Software requirements
2. [Setup your PC](http://ms-iot.github.io/content/en-US/win10/SetupPCRPI.htm).
3. [Setup your Rasberry Pi 2](http://ms-iot.github.io/content/en-US/win10/SetupRPI.htm).
4. [Enabling Internet Connection Sharing](http://ms-iot.github.io/content/en-US/win10/ConnectToDevice.htm).
	- The advantage of using ISC is that each device is isolated behind the NAT provided by ISC.
5. [GHI Electronics FEZ HAT](https://www.ghielectronics.com/catalog/product/500)

##NETWORK CONNECTIVITY

###Ethernet
Connecting multiple Raspberry Pi 2s to the internet is most easily achieved via Ethernet and Windows Internet Connection Sharing.  This provides a fast link, great for app deployment to the device, a great debugging experience and shares the PC internet connection with the Raspberry Pi.  ICS is also useful as it hides the devices from the network and isolates its use just to the local developer.

1. Ethernet Cable
2. USB Ethernet Dongle if you lack an Ethernet connection on you PC

###Wi-Fi
There is great Wi-Fi support on the Raspberry Pi running Windows 10 IoT Core.  However, in a lab environment where there are multiple development machines and Raspberry Pi 2s the setup is more complex as you need a way to isolate and provide a link between each Raspberry Pis and their corresponding PC.
This can be achieved by 
1.	Setting the Raspberry Pi address in Visual Studio remote client configuration.
2.	Rename each Raspberry Pi and use that unique name in the Visual Studio remote client configuration.
3.	If you want to use a consistent Raspberry Pi name then you could map the Raspberry Pi IP address in the PC Host file.

Raspberry [Wi-Fi compatible dongles](http://ms-iot.github.io/content/en-US/win10/SupportedInterfaces.htm) 



#Install Maker Den on to PC

1. First install [GitHub Windows Client Tools](http://git-scm.com/download/win)
2. Create a local bootstrap.bat file and copy and paste the contents of the [Maker Den Bootstrap](https://raw.githubusercontent.com/MakerDen/IoT-Maker-Den-Documentation-and-Guides/master/Resources/Setup/Bootstrap.bat) into the .bat file.
3. Run the Bootstrap.bat file. It will clone the documentation and resources to the local devices.
4. In Visual Studio restore the Nuget cache by building the project
5. Add the Maker Den Snippets from Tools -> Code Snippet Manager -> Add -> Navigate to the \Documents\Visual Studio 2015\Code Snippets\Visual C#\MakerDen folder and click select
6. Exit from Visual Studio
7. Run the Reset MakerDen shortcut on the desktop.  This will start Visual Studio, Load the project and your Snippets, Shortcuts and Nuget Cache should now be in place.

You are ready to have fun with the Maker Den
	 