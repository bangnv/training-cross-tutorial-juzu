@Assets(scripts = {
		@Script(id = "jquery", src = "jquery-1.7.1.min.js", location = AssetLocation.CLASSPATH),
		@Script(src = "main.js", location = AssetLocation.CLASSPATH, depends = "jquery") })
//@Assets(scripts = {@Script(id = "jquery", src = "js/jquery-1.7.1.min.js"),
//		@Script(id = "jquery", src = "js/main.js", depends = { "jquery" }) })
@Application
@Portlet
@Bindings({
		@Binding(value = org.exoplatform.services.jcr.RepositoryService.class, implementation = GateInMetaProvider.class),
		@Binding(value = org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator.class, implementation = GateInMetaProvider.class) })
package org.exoplatform.cross.galleryphotos;

import juzu.Application;
import juzu.plugin.asset.Assets;
import juzu.plugin.asset.Script;
import juzu.plugin.binding.Binding;
import juzu.plugin.binding.Bindings;
import juzu.plugin.portlet.Portlet;
import juzu.asset.AssetLocation;	
import org.exoplatform.cross.galleryphotos.model.GateInMetaProvider;

