# IIB-WESB-BOMAP-CONV
WESB BO Map to IIB Graphical Map converstion utilities to accelerate the BO Map conversion
#
This utility uses IIB v9.0 message flows and ESQL to convert WESB BO Map files (XML) to IIB Graphical Data Mapper files (XML).
There are two IIB workspaces provided.
1) The CONVERTn workspace contains the IIB message flows that will perform the transformation of BO Map XML files to IBM GDM XML files. The messages flows use FileInput and FileOutput nodes to read and write the source and target XML files.
The File I/O nodes are configured to target the 2nd IIB workspace
2) the TARGET workspace which contains a WESBSourceResourcesApp Application which contains WESB mediation and BO Maps and the XML Schema definitions that are reference by them. There is a placehold ConvertedResourcesApp application which is where the converted artefacts will be placed so they can be openned in the IIB Toolkit/Studio.

In order to run the utility you will need to create the directory structure C:\WESBCONVERT and create the 2 workspaces from there. If you do not do this you will need to simply reconfigure the File I/O nodes in the IIB message flows in the CONVERTn workspace before deploying and starting the message flows.

This utility is a work in progess and at this point in time it will attempt to do the following:
1) Create an IIB graphical map for each BOMap in the TARGET\WESBSourceResourcesApp
2) Associate the correct XML Schema for the input and output sides of the map.
3) Harvest any Java code from the source BO Map and write it to a separate Java file
4) For simple maps that contain source to target moves only those moves/associations will be created.

Therefore, at this point in time the utility represents a rudimentary capability that demonstrates how to navigate BO Map XML and create IIB GDM XML.

Detailed documentation can be found in the PDFs located in the CONVMDocumentation folder. Sample test maps and data are provided.

If you wish to contribute please email Dave - davearno@au1.ibm.com
