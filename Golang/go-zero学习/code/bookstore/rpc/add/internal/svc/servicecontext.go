package svc

import (
	"bookstore/rpc/add/internal/config"
	"bookstore/rpc/model"

	"github.com/zeromicro/go-zero/core/stores/sqlx"
)

type ServiceContext struct {
	c     config.Config
	Model *model.BookModel
}

func NewServiceContext(c config.Config) *ServiceContext {
	return &ServiceContext{
		c:     c,
		Model: model.NewBookModel(sqlx.NewMysql(c.DataSource), c.Cache, c.Table),
	}
}
