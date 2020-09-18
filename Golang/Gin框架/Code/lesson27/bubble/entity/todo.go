/**
 * @Description todo实体类
 * @Author 陌溪
 * @Date 2020/9/18 20:18
 **/
package entity

import (
	"bubble/dao"
)

type Todo struct {
	ID int `json:"id"`
	Title string `json:"title"`
	Status bool `json:"status"`
}

/**
 * @Description 增加todo
 * @Param
 * @return
 **/
func CreateTodo(todo *Todo) (err error) {
	err = dao.DB.Create(&todo).Error
	// 响应
	if err != nil {
		return err
	}
	return
}

/**
 * @Description 获取todo列表
 * @Param
 * @return
 **/
func GetTodoList() (todoList[] *Todo, err error) {
	err = dao.DB.Find(&todoList).Error
	if err != nil {
		return nil, err
	}
	return todoList, nil
}

/**
 * @Description 获取Todo通过ID
 * @Param 
 * @return 
 **/
func GetTodoById(id string) (todo *Todo, err error) {
	todo = new(Todo)
	err = dao.DB.Where("id=?", id).Find(&todo).Error
	if err != nil {
		return nil, err
	}
	return todo, nil
}


/**
 * @Description 获取todo列表
 * @Param
 * @return
 **/
func UpdateTodo(todo *Todo) (err error) {
	err = dao.DB.Save(&todo).Error
	return
}

/**
 * @Description 获取todo列表
 * @Param
 * @return
 **/
func DeleteTodo(id string) (err error) {
	err = dao.DB.Where("id=?", id).Delete(&Todo{}).Error
	return
}