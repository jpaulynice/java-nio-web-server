var headerView = Backbone.Marionette.ItemView.extend({
	template: "#headerTemplate",
	
	events: {
		"click .home" :"showHome",
		"click .features": "showFeatures",
		"click .contact" : "showContact"
	},
	
	showHome: function(e){
		this.makeViewActive(e);
		this.trigger('app:showHome');
	},
	
	showFeatures: function(e){
		this.makeViewActive(e);
		this.trigger('app:showFeatures');
	},
	
	showContact: function(e){
		this.makeViewActive(e);
		this.trigger('app:showContact');
	},
	
	makeViewActive: function(e){
		var target = $(e.currentTarget).data("id");
		this.$('.masthead-nav li').removeClass('active');
		this.$('.'+target).addClass('active');
	}
	
});

var footerView = Backbone.Marionette.ItemView.extend({
	template: "#footerTemplate"
});

var homeView = Backbone.Marionette.ItemView.extend({
	template: "#homeTemplate",
});


var contactView = Backbone.Marionette.ItemView.extend({
	template: "#contactTemplate"
});

var featuresView = Backbone.Marionette.ItemView.extend({
	template: "#featuresTemplate"
});

var learnMoreView = Backbone.Marionette.ItemView.extend({
	template: "#learnMoreTemplate"
});