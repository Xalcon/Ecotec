// woohoo, polyfills
if(typeof JSON!=='object'){JSON={};}(function(){'use strict';function f(n){return n<10?'0'+n:n;}function this_value(){return this.valueOf();}if(typeof Date.prototype.toJSON!=='function'){Date.prototype.toJSON=function(){return isFinite(this.valueOf())?this.getUTCFullYear()+'-'+f(this.getUTCMonth()+1)+'-'+f(this.getUTCDate())+'T'+f(this.getUTCHours())+':'+f(this.getUTCMinutes())+':'+f(this.getUTCSeconds())+'Z':null;};Boolean.prototype.toJSON=this_value;Number.prototype.toJSON=this_value;String.prototype.toJSON=this_value;}var cx,escapable,gap,indent,meta,rep;function quote(string){escapable.lastIndex=0;return escapable.test(string)?'"'+string.replace(escapable,function(a){var c=meta[a];return typeof c==='string'?c:'\\u'+('0000'+a.charCodeAt(0).toString(16)).slice(-4);})+'"':'"'+string+'"';}function str(key,holder){var i,k,v,length,mind=gap,partial,value=holder[key];if(value&&typeof value==='object'&&typeof value.toJSON==='function'){value=value.toJSON(key);}if(typeof rep==='function'){value=rep.call(holder,key,value);}switch(typeof value){case'string':return quote(value);case'number':return isFinite(value)?String(value):'null';case'boolean':case'null':return String(value);case'object':if(!value){return'null';}gap+=indent;partial=[];if(Object.prototype.toString.apply(value)==='[object Array]'){length=value.length;for(i=0;i<length;i+=1){partial[i]=str(i,value)||'null';}v=partial.length===0?'[]':gap?'[\n'+gap+partial.join(',\n'+gap)+'\n'+mind+']':'['+partial.join(',')+']';gap=mind;return v;}if(rep&&typeof rep==='object'){length=rep.length;for(i=0;i<length;i+=1){if(typeof rep[i]==='string'){k=rep[i];v=str(k,value);if(v){partial.push(quote(k)+(gap?': ':':')+v);}}}}else{for(k in value){if(Object.prototype.hasOwnProperty.call(value,k)){v=str(k,value);if(v){partial.push(quote(k)+(gap?': ':':')+v);}}}}v=partial.length===0?'{}':gap?'{\n'+gap+partial.join(',\n'+gap)+'\n'+mind+'}':'{'+partial.join(',')+'}';gap=mind;return v;}}if(typeof JSON.stringify!=='function'){escapable=/[\\\"\u0000-\u001f\u007f-\u009f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;meta={'\b':'\\b','\t':'\\t','\n':'\\n','\f':'\\f','\r':'\\r','"':'\\"','\\':'\\\\'};JSON.stringify=function(value,replacer,space){var i;gap='';indent='';if(typeof space==='number'){for(i=0;i<space;i+=1){indent+=' ';}}else if(typeof space==='string'){indent=space;}rep=replacer;if(replacer&&typeof replacer!=='function'&&(typeof replacer!=='object'||typeof replacer.length!=='number')){throw new Error('JSON.stringify');}return str('',{'':value});};}if(typeof JSON.parse!=='function'){cx=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;JSON.parse=function(text,reviver){var j;function walk(holder,key){var k,v,value=holder[key];if(value&&typeof value==='object'){for(k in value){if(Object.prototype.hasOwnProperty.call(value,k)){v=walk(value,k);if(v!==undefined){value[k]=v;}else{delete value[k];}}}}return reviver.call(holder,key,value);}text=String(text);cx.lastIndex=0;if(cx.test(text)){text=text.replace(cx,function(a){return'\\u'+('0000'+a.charCodeAt(0).toString(16)).slice(-4);});}if(/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,'@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,']').replace(/(?:^|:|,)(?:\s*\[)+/g,''))){j=eval('('+text+')');return typeof reviver==='function'?walk({'':j},''):j;}throw new SyntaxError('JSON.parse');};}}());

function disableAll(doc, category)
{
	var catSet = doc.layerSets.getByName(category);
	if(category == null) return;
	for(var e = 0; e < catSet.layerSets.length; e++)
		catSet.layerSets[e].visible = false;
}

function enableGroup(doc, category, group)
{
	try
	{
		var catSet = doc.layerSets.getByName(category);
		if(category == null) return;
		var name = group;
		if(group.name)
			name = group.name;
		if(name == "TBD") return;
		catSet.layerSets.getByName(name).visible = true;

		if(group.color)
		{
			applyColor(doc, category, group.name, parseInt(group.color));
		}
	}
	catch(e)
	{
		throw "Unable to enable group '"+category+" -> "+name+"'";
	}
}

function applyColor(doc, category, group, color)
{
	try
	{
		var cat = doc.layerSets.getByName(category);
		var grp = cat.layerSets.getByName(group);
		var layer = grp.artLayers.getByName("Color");
		doc.activeLayer = layer;
	}
	catch(e)
	{
		throw "Unable to apply color - 'Color' layer is missing in '"+category+"->"+group+"': " + e + " # " + e.stack;
	}
	var solidColor = new SolidColor();
	solidColor.rgb.alpha = (color >> 24) & 0xFF;
	solidColor.rgb.red = (color >> 16) & 0xFF;
	solidColor.rgb.green = (color >> 8) & 0xFF;
	solidColor.rgb.blue = (color >> 0) & 0xFF;
	doc.selection.selectAll();
	doc.selection.fill(solidColor);
	doc.selection.deselect();
}

(function(doc)
{
	var file = File.openDialog("Select texture definition file", "*.json");
	if(file == null) return;
	file.open("r");
	var json = "";
	while(!file.eof)
		json += file.readln();
	file.close();
	var data = JSON.parse(json);
	
	var targetDir = Folder.selectDialog("Save textures to", file.path);
	if(targetDir == null) return;
	
	var pngOpts = new PNGSaveOptions();
	pngOpts.compression = 9;
	pngOpts.transparency = true;
	
	var win = new Window("window{text:'Progress',bounds:[100,100,400,150],bar:Progressbar{bounds:[20,20,280,31] , value:0,maxvalue:100}};");
	win.show();

	for(var i = 0; i < data.sets.length; i++)
	{
		var set = data.sets[i];
		disableAll(doc, "Elements");
		disableAll(doc, "Generics");
		disableAll(doc, "Details");
		
		if(set.elements)
			for(var s = 0; s < set.elements.length; s++)
				enableGroup(doc, "Elements", set.elements[s]);
		if(set.generics)
			for(var s = 0; s < set.generics.length; s++)
				enableGroup(doc, "Generics", set.generics[s]);
		if(set.details)				
			for(var s = 0; s < set.details.length; s++)
				enableGroup(doc, "Details", set.details[s]);
		
		var outFile = new File(targetDir + "/" + set.name + ".png");
		doc.saveAs(outFile, pngOpts, true);

		win.bar.value = (i * 100 / data.sets.length) | 0;
	}

	win.close();
})(activeDocument);


