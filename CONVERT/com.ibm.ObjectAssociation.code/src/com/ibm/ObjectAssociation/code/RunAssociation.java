/* Copyright (c) 2014 IBM Corporation and other Contributors

 All rights reserved. This program and the accompanying materials
 are made available under the terms of the Eclipse Public License v1.0
 which accompanies this distribution, and is available at
 http://www.eclipse.org/legal/epl-v10.html
 
 Contributors:
     IBM - initial implementation */
package com.ibm.ObjectAssociation.code;
// Example .map file to mapping node associate pattern
// This is for example purposes only. It is very like that a customer will need to customize
// for their own naming conventions or introduce a configuration file to provide the relationship
// between mapping nodes (formerly BO Primitives) and the map files they actually invoke
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import com.ibm.broker.config.appdev.FlowRendererMSGFLOW;
import com.ibm.broker.config.appdev.MessageFlow;
import com.ibm.broker.config.appdev.Node;
import com.ibm.broker.config.appdev.nodes.MappingMSLNode;
import com.ibm.broker.config.appdev.patterns.*;
import com.ibm.broker.config.appdev.MessageMSLMap;

public class RunAssociation implements GeneratePatternInstanceTransform {
	
	private static String _msgflow = ".msgflow";
	private static String _map = ".map";
	
	Vector<String> flowFileNames = new Vector<String>();
	Vector<String> mapFileNames = new Vector<String>();
	Vector<MessageMSLMap> maps = new Vector<MessageMSLMap>();
	Vector<MessageFlow> flows = new Vector<MessageFlow>();
	
	@Override
	public void onGeneratePatternInstance(
			PatternInstanceManager patternInstanceManager) {

		// The location for the generated projects 
		String location = patternInstanceManager.getWorkspaceLocation();

		// The pattern instance name for this generation
		@SuppressWarnings("unused")
		String patternInstanceName = patternInstanceManager
				.getPatternInstanceName();
	
		System.out.println("Traversing " + location);
		traverse(new File(location));
		
		Iterator<MessageFlow> flowsI = flows.iterator();


		for (int f = 0; f < flows.size(); f++)
		{
			boolean isDirty = false;
			Node candidate = null;
			MessageFlow flow = (MessageFlow) flowsI.next();
			Vector<Node> nodes = flow.getNodes();
			
			for (int i = 0 ; i < nodes.size(); i++)
			{ //traverse and look for mapping nodes...
				//need to match the name of the node to the map name somehow??
				//in reality we may need a configuration file to provide the relationships as input
				candidate = (Node) nodes.elementAt(i);
				if (candidate instanceof MappingMSLNode)
				{
					// cycle through the map names and see if the map name 
					// is contained in the Node Name
					for (int j = 0 ; j < maps.size(); j++)
					{
						MessageMSLMap map = maps.elementAt(j);
						if (map.getName().contains(candidate.getNodeName()))
						{
							isDirty = true;
							((MappingMSLNode) candidate).setMappingExpression(map);
							System.out.println("Adding map " + map.getName()+ " to flow " + flow.getName());
						}
					}
				}
					
					
			}
			try{
			if (isDirty)
			{
				String path = flowFileNames.elementAt(f);
				System.out.println(flowFileNames.elementAt(f) + ", " + flow.getName());
				//String path = flowFileNames.elementAt(f).substring(0, flowFileNames.elementAt(f).lastIndexOf('\\' - 1));
				FlowRendererMSGFLOW.write(flow, path);
				isDirty = false;
			}
			}
			catch (Exception ex){
				
				System.out.println("Exception Writing Flow " + ex.getMessage());
			}
			
		}
		
	}
	
	private void traverse (File directory)
	{
		
		
		File[] files = directory.listFiles();
	try
	{
		for (File file : files)
		{
			if (file.isDirectory())
			{
				// Its a directory so (recursively) traverse it

				traverse(file);
			}
			else
			{
				if (file.getName().endsWith(_msgflow))
				{
					flowFileNames.add(file.getParent());
					flows.add(FlowRendererMSGFLOW.read(file));
				}	
				if (file.getName().endsWith(_map))
				{
					mapFileNames.add(file.getAbsolutePath());
					maps.add(new MessageMSLMap(file));
					System.out.println("Adding map " + file.getName());
				}
				
				
			}
		}
	}
	catch (Exception ex)
	{}
	
	
	}
		
		
		
		
	

}
