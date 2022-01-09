module Api
    module V1
        class PostsController < ApplicationController
            def index
                posts = Post.order('created_at DESC');
                render json: {status: 'SUCCESS', message: 'Successfully loaded posts', data:posts}, status: :ok
            end

            def show
                post = Post.find(params[:id])
                render json: {status: 'SUCCESS', message: 'Successfully loaded post', data:post}, status: :ok
            end

            def create
                post = Post.new(post_params)
                if post.save
                    render json: {status: 'SUCCESS', message: 'Successfully created post', data:post}, status: :ok
                else
                    render json: {status: 'ERROR', message: 'Failed to create post', data:post.errors}, status: :unprocessable_entry
                end
            end

            def destroy
                post = Post.find(params[:id])
                post.destroy

                render json: {status: 'SUCCESS', message: 'Successfully deleted post', data:post}, status: :ok
            end

            def update
                post = Post.find(params[:id])
                if post.update(post_params)
                    render json: {status: 'SUCCESS', message: 'Successfully updated post', data:post}, status: :ok
                else
                    render json: {status: 'ERROR', message: 'Failed to update post', data:post.errors}, status: :unprocessable_entry
                end
            end

            private

            def post_params
                params.permit(:title, :body)
            end
        end
    end
end