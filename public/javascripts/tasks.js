function Task(data) {
	this.id = data.id;
	this.label = ko.observable(data.label);
}

function TasksViewModel() {
	var self = this;
	self.tasks = ko.observableArray([]);

	$.getJSON("/tasks", function(data) {
		var mapped = $.map(data, function(item) { console.log(item); return new Task(item); });
		self.tasks(mapped);
	});
}

ko.applyBindings(new TasksViewModel());