import React from "react";
import { Comment } from "semantic-ui-react";

const DealComment = ({ text, author, date }) => (
  <Comment.Group>
    <Comment>
      <Comment.Content style={{borderWidth:  5,  borderStyle:  'inset'}}>
        <Comment.Author style={{borderWidth:  5,  borderStyle:  'outset'}}><b>{author} </b> on {date}</Comment.Author>
        <Comment.Text>{text}</Comment.Text>
      </Comment.Content>
    </Comment>
	<br/>
  </Comment.Group>
);
export default DealComment;
