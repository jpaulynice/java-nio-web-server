jSoftApp = new Backbone.Marionette.Application();

jSoftApp.addRegions({
	headerNav  : ".masthead",
	mainRegion : ".mainContent",
	featuresRegion: ".site-wrapper-inner",
	footer     : ".mastfoot"
});

var controller = new jSoftController(jSoftApp);
jSoftApp.start();