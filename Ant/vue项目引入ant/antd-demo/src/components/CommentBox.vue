<template>
    <div>
        <a-comment>
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
                    <a-button style="margin-left:5px;"  @click="handleCancle">
                        取消评论
                    </a-button>
                </a-form-item>
            </div>
        </a-comment>
    </div>
</template>
<script>
    import moment from 'moment';
    export default {
        props: ['userInfo', 'replyInfo'],
        data() {
            return {
                comments: [],
                submitting: false,
                value: '',
                moment,
            };
        },
        methods: {
            handleSubmit() {
                this.comments = {
                    replyCommentUid: this.replyInfo.uid,
                    replyUid: this.replyInfo.replyUid,
                    userName: this.userInfo.userName,
                    avatar: this.userInfo.avatar,
                    content: this.value,
                }
                this.value = '';
                this.$emit("submit-box", this.comments)
            },
            handleChange(e) {
                this.value = e.target.value;
            },
            handleCancle() {
                this.value = '';
                this.$emit("cancleBox", this.replyInfo.uid)
            }
        },
    };
</script>
