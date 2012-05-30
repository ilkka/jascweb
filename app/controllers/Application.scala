package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {
  
	val taskForm = Form(
		"label" -> nonEmptyText
	)

  	def index = Action {
    	//Ok(views.html.index("Your new application is ready."))
    	Redirect(routes.Application.tasks)
  	}

	def tasks = Action {
		Ok(views.html.index(taskForm))
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