function Task(data) {
	this.id = ko.observable(data.id);
	this.label = ko.observable(data.label);
}

function TasksViewModel() {
	var self = this;
	self.tasks = ko.observableArray([]);
	self.newTaskText = ko.observable("");

	self.saveAndUpdate = function() {
		$.ajax("/tasks", {
			data: ko.toJSON({ tasks: self.tasks }),
			type: "post", contentType: "application/json",
			success: function(result) {
				self.tasks($.map(result, function(item) { return new Task(item); }));
			}
		});
	}
	
	self.create = function() {
		self.tasks.push(new Task({ label: self.newTaskText() }));
		self.newTaskText("");
		self.saveAndUpdate();
	}

	self.delete = function(task) {
		self.tasks.destroy(task);
		self.saveAndUpdate();
	}

	$.getJSON("/tasks", function(data) {
		var mapped = $.map(data, function(item) { return new Task(item); });
		self.tasks(mapped);
	});
}

ko.applyBindings(new TasksViewModel());