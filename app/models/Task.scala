package models

import play.api.db._
import play.api.Play.current
//import play.api.libs.json._

import anorm._
import anorm.SqlParser._

case class Task(id: Long, label: String, _destroy: Boolean = false)

object Task {
	val task = {
		get[Long]("id") ~
		get[String]("label") map {
			case id~label => Task(id, label)
		}
	}

	def all(): List[Task] = DB.withConnection { implicit c =>
		SQL("select * from task order by id").as(task *)
	}

	def create(label: String) {
		DB.withConnection { implicit c =>
			SQL("insert into task (label) values ({label})").on(
				'label -> label
				).executeUpdate()
		}
	}

	def delete(id: Long) {
		DB.withConnection { implicit c =>
			SQL("delete from task where id={id}").on(
				'id -> id
				).executeUpdate()
		}
	}

	// def reads(json: JsValue): Task = Task(
	// 	(json \ "id").as[Long],
	// 	(json \ "label").as[String],
	// 	(json \ "_destroy").as[Boolean]
	// 	)

	// def writes(t: Task): JsValue = JsObject(Seq(
	// 		"id" -> JsNumber(t.id),
	// 		"label" -> JsString(t.label),
	// 		"_destroy" -> JsBoolean(t._destroy)
	// 		))
}