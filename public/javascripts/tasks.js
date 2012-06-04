function Task(data) {
	this.id = data.id;
	this.label = ko.observable(data.label);
}

function TasksViewModel() {
	var self = this;
	self.tasks = ko.observableArray([]);
	self.newTaskText = ko.observable("");

	self.create = function() {
		self.tasks.push(new Task({ label: self.newTaskText() }));
		self.newTaskText("");
		 $.ajax("/tasks", {
		 	data: ko.toJSON({ tasks: self.tasks }),
		 	type: "post", contentType: "application/json",
		 	success: function(result) { /* it'd be nice to flash a message here */ }
		 });
	}

	$.getJSON("/tasks", function(data) {
		var mapped = $.map(data, function(item) { console.log(item); return new Task(item); });
		self.tasks(mapped);
	});
}

ko.applyBindings(new TasksViewModel());