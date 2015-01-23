# IIB-WESB-BOMAP-CONV
WESB BO Map to IIB Graphical Map converstion utilities to accelerate the BO Map conversion
#### Materials being prepared for publication - watch this space ####
This utility uses IIB v9.0 message flows and ESQL to convert WESB BO Map files (XML) to IIB Graphical Data Mapper files (XML).
There are two IIB workspaces provided.
1) The CONVERTn workspace contains the IIB message flows that will perform the transformation of BO Map XML files to IBM GDM XML files. The messages flows use FileInput and FileOutput nodes to read and write the source and target XML files.
The File I/O nodes are configured to target the 2nd IIB workspace
2) the TARGET workspace which contains a WESBSourceResourcesApp Application which contains WESB mediation and BO Maps and the XML Schema definitions that are reference by them. There is a placehold ConvertedResourcesApp application which is where the converted artefacts will be placed so they can be openned in the IIB Toolkit/Studio.

In order to run the utility you will need to create the directory structure C:\WESBCONVERT and create the 2 workspaces from there. If you do not do this you will need to simply reconfigure the File I/O nodes in the IIB message flows in the CONVERTn workspace before deploying and starting the message flows.

The BOMap conversion and Java harvester is: message File_WESBMaptoGDMMapJavaHarvester.msgflow

This utility is a work in progess and at this point in time it will attempt to do the following:
1) Create an IIB graphical map for each BOMap in the TARGET\WESBSourceResourcesApp
2) Associate the correct XML Schema for the input and output sides of the map.
3) Harvest any Java code from the source BO Map and write it to a separate Java file
4) For simple maps that contain source to target moves only those moves/associations will be created.

The BOMap conversion and Java harvester is: message File_WESBMaptoGDMMapJavaHarvester.msgflow

Therefore, at this point in time the utility represents a rudimentary capability that demonstrates how to navigate BO Map XML and create IIB GDM XML.

Additional to the BOMap conversion message flow I have included to other useful utilities to help in the WESB to IIB conversion process.

The WESB meditaion file parser : File_WESBMedFlowParseBuildMapDetail.msgflow

This message flow parses the WESB .medflow file and captures details about all/any WESB BOMaps that the mediation flow calls as part of its processing and writes them to a file. This information is useful for the re-associtation of converted BOMap, IIB GDM equivalents with the IIB messages flows that will call them. 
The IIB WESB Conversion tool (shipped with IIB) will convert WESB mediations to message flows and place an un configured Mapping node in the flow as a place holder. 

The ObjectAssociation pattern : This is an IIB pattern that includes Java code written to the IBM Integration API which allows for the programatic creation and manipulation of IIB objects such as message flows. The pattern is an example of how you might use the API to associate newly converted IIB GDM maps with IIB message flows converted from WESB mediations. This ObjectAssociation pattern could be extended to use the meta-data captured by the File_WESBMedFlowParseBuildMapDetail.msgflow.

More detailed documentation can be found in the PDFs located in the CONVDocumentation folder. Sample test maps and data are provided.

If you wish to contribute please email Dave - davearno@au1.ibm.com
