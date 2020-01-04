<template>
    <div>
        <a-comment v-for="item in comments" :key="item.uid">
            <span slot="actions" @click="replyTo(item.uid)">回复</span>
            <a slot="author">{{item.userName}}</a>
            <a-avatar
                    slot="avatar"
                    :src="item.avatar"
                    :alt="item.userName"
            />
            <p slot="content">
                {{item.content}}
            </p>

            <a-comment class="comment" :id="item.uid" :reply-info="replyInfo">
                <a-avatar
                        slot="avatar"
                        :src="userInfo.avatar"
                        alt="Han Solo"
                />
                <div slot="content">
                    <a-form-item>
                        <a-textarea :rows="4" @change="handleChange" :value="value"></a-textarea>
                    </a-form-item>
                    <a-form-item>
                        <a-button htmlType="submit" :loading="submitting" @click="handleSubmit" type="primary">
                            添加评论
                        </a-button>
                        <a-button style="margin-left:5px;" @click="handleCancle">
                            取消评论
                        </a-button>
                    </a-form-item>
                </div>
            </a-comment>

            <CommentList :comments="item.reply"></CommentList>

        </a-comment>
    </div>
</template>
<script>

    import { mapMutations} from 'vuex';

    export default {
        name: "CommentList",
        props: ['comments'],
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
                    uid: "uid000002",
                    blogUid: "uid000003",
                    replyUserUid: "uid000004",
                    avatar: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                },
            };
        },
        created() {

        },
        components: {

        },

        compute: {

        },
        methods: {
            ...mapMutations(['setCommentList']),
            replyTo: function (uid) {
                var lists = document.getElementsByClassName("comment");
                for (var i = 0; i < lists.length; i++) {
                    lists[i].style.display = 'none';
                }
                document.getElementById(uid).style.display = 'block';
                this.replyInfo.uid = uid
            },

            handleSubmit() {

                this.comment = {
                    replyCommentUid: this.replyInfo.uid,
                    uid: 'uid008',
                    userName: '小溪',
                    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
                    content: this.value,
                    reply: []
                }

                this.value = '';

                document.getElementById(this.replyInfo.uid).style.display = 'none'

                var comments = this.$store.state.app.commentList;

                this.getMenuBtnList(comments, this.comment.replyCommentUid, this.comment)

                this.$store.commit("setCommentList", comments);

            },
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
            handleChange(e) {
                this.value = e.target.value;
            },
            handleCancle() {
                this.value = '';
                document.getElementById(this.replyInfo.uid).style.display = 'none'
            }
        },
    };
</script>
<style>
    .comment {
        display: none;
    }
</style>
