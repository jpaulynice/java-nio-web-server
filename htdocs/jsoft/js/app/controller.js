var jSoftController = Backbone.Marionette.Controller.extend({
	initialize: function(options){
		this.options = options || {};
		
		this.header = new headerView();
		this.options.headerNav.show(this.header);
		
		this.home = new homeView();
		this.options.mainRegion.show(this.home);

		this.footer = new footerView();
		this.options.footer.show(this.footer);
		
		this.features = new featuresView();
		this.contact = new contactView();

		
		this.listenTo(this.header, 'app:showFeatures', this.showFeatures);
		this.listenTo(this.header, 'app:showHome', this.showHome);
		this.listenTo(this.header, 'app:showContact', this.showContact);
	},
	
	showHome: function(){
		this.options.mainRegion.show(this.home);
	},
	
	showFeatures:function(e){
		this.options.mainRegion.show(this.features);
	},
	
	showContact: function(e){
		this.options.mainRegion.show(this.contact);
	}
});