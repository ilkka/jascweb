package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.libs.Json._
import com.codahale.jerkson.Json

object Application extends Controller {
  
  	def index = Action {
		Ok(views.html.index())
  	}

	def tasks = Action {
		val tasks = models.Task.all()
		val json = Json.generate(tasks)
		Ok(json).as("application/json")
	}

	def saveTasks = Action(parse.json) { implicit request =>
		(request.body \ "tasks").asOpt[List[models.Task]].foreach { task =>
			if (task.id.isDefined()) {
				// update
			} else {
				models.Task.create(task.label)
			}
		}
	}

	def deleteTask(id: Long) = Action {
		models.Task.delete(id)
		Redirect(routes.Application.index)
	}

}