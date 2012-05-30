package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import com.codahale.jerkson.Json

object Application extends Controller {
  
	val taskForm = Form(
		"label" -> nonEmptyText
	)

  	def index = Action {
		Ok(views.html.index(taskForm))
  	}

	def tasks = Action {
		val tasks = models.Task.all()
		val json = Json.generate(tasks)
		Ok(json).as("application/json")
	}

	def newTask = Action { implicit request =>
		taskForm.bindFromRequest.fold(
			errors => BadRequest(views.html.index(errors)),
			label => {
				models.Task.create(label)
				Redirect(routes.Application.tasks)
			}
		)
	}

	def deleteTask(id: Long) = Action {
		models.Task.delete(id)
		Redirect(routes.Application.tasks)
	}

}