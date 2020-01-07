<template>
    <div>

        <CommentBox :userInfo="userInfo" :reply-info="replyInfo"  @submit-box="submitBox" @canel-box="cancelBox"></CommentBox>

        <CommentList :comments="comments"></CommentList>
    </div>
</template>
<script>
    import CommentList from "../components/CommentList";
    import CommentBox from "../components/CommentBox";

    // vuex中有mapState方法，相当于我们能够使用它的getset方法
    import {mapMutations} from 'vuex';

    export default {
        data() {
            return {
                submitting: false,
                value: '',
                userInfo: {
                    uid: "uid000001",
                    userName: "张三",
                    avatar: "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"

                },
                replyInfo: {
                    uid: "uid009",
                    blogUid: "uid000003",
                    replyUserUid: "uid000004",
                    replyUid: "",
                    avatar: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                },
                comments: [
                    {
                        uid: 'uid000',
                        userName: "陌溪",
                        avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
                        content: '我是一级评论',
                        reply: [
                            {
                                uid: 'uid001',
                                userName: "陌溪",
                                avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
                                content: '我是二级评论',
                                reply: [
                                    {
                                        uid: 'uid002',
                                        userName: "陌溪",
                                        avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
                                        content: '我是三级评论',
                                        reply: []
                                    }
                                ]

                            }, {
                                uid: 'uid003',
                                userName: "陌溪",
                                avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
                                content: '我是二级评论',
                                reply: []
                            }
                        ]
                    },
                    {
                        uid: 'uid004',
                        userName: "陌溪",
                        avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
                        content: '我是一级评论',
                        reply: [
                            {
                                uid: 'uid005',
                                userName: "陌溪",
                                avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
                                content: '我是二级评论',
                                reply: []

                            }
                        ]
                    },
                ],
            };
        },
        watch: {},
        computed: {

        },
        components: {
            CommentList,
            CommentBox
        },
        mounted() {
            this.setCommentList(this.comments);
        },
        methods: {
            //拿到vuex中的写的两个方法
            ...mapMutations(['setCommentList']),
            getMenuBtnList(menuTreeList, uid, comment) {

                if (menuTreeList == undefined || menuTreeList.length <= 0) {
                    return;
                }

                for (let item of menuTreeList) {

                    if (item.uid === uid) {
                        var menu = item.reply;
                        menu.push(comment);
                    } else {
                        this.getMenuBtnList(item.reply, uid, comment);
                    }
                }
            },
            submitBox(e) {
                console.log("提交");
                var comments = this.$store.state.app.commentList;
                comments.push(e);
                this.$store.commit("setCommentList", comments);
            },
            cancelBox(e) {
                console.log("取消", e)
            },
        },
    };
</script>
