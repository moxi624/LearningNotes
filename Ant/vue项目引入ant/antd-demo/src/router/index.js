import HelloWorld from '../components/HelloWorld'
import index from '../view/index'
import about from '../view/about'
import comment from '../view/comment'
import heatMap from '../view/heatMap'

import Router from 'vue-router'

export default new Router({
    routes: [

        {
            path: '/',
            name: 'HelloWorld',
            component: HelloWorld
        },
        {
            path: '/index',
            name: 'index',
            component: index
        },
        {
            path: '/about',
            name: 'about',
            component: about
        },
        {
            path: '/comment',
            name: 'comment',
            component: comment
        },
        {
            path: '/heatMap',
            name: 'heatMap',
            component: heatMap
        },
    ]
})
