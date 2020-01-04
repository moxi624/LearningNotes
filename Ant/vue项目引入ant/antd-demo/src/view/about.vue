<template>
    <a-comment>
        <span slot="actions">Reply to</span>
        <a slot="author">Han Solo</a>
        <a-avatar
                slot="avatar"
                src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                alt="Han Solo"
        />
        <p slot="content">
            We supply a series of design principles, practical patterns and high quality design resources
            (Sketch and Axure).
        </p>
        <a-comment>
            <span slot="actions">Reply to</span>
            <a slot="author">Han Solo</a>
            <a-avatar
                    slot="avatar"
                    src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                    alt="Han Solo"
            />
            <p slot="content">
                We supply a series of design principles, practical patterns and high quality design
                resources (Sketch and Axure).
            </p>
            <a-comment>
                <span slot="actions">Reply to</span>
                <a slot="author">Han Solo</a>
                <a-avatar
                        slot="avatar"
                        src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                        alt="Han Solo"
                />
                <p slot="content">
                    We supply a series of design principles, practical patterns and high quality design
                    resources (Sketch and Axure).
                </p>
            </a-comment>
            <a-comment>
                <span slot="actions">Reply to</span>
                <a slot="author">Han Solo</a>
                <a-avatar
                        slot="avatar"
                        src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                        alt="Han Solo"
                />
                <p slot="content">
                    We supply a series of design principles, practical patterns and high quality design
                    resources (Sketch and Axure).
                </p>
            </a-comment>
        </a-comment>
    </a-comment>
</template>
<script>
    import moment from 'moment';
    export default {
        data() {
            return {
                likes: 0,
                dislikes: 0,
                action: null,
                moment,
            };
        },
        methods: {
            like() {
                this.likes = 1;
                this.dislikes = 0;
                this.action = 'liked';
            },
            dislike() {
                this.likes = 0;
                this.dislikes = 1;
                this.action = 'disliked';
            },
        },
    };
</script>
