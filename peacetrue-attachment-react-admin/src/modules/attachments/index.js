import React from "react";
import {Resource} from "react-admin";

import {AttachmentList} from './list';
import {AttachmentCreate} from './create';
import {AttachmentEdit} from './edit';
import {AttachmentShow} from './show';

let Attachment = {list: AttachmentList, create: AttachmentCreate, edit: AttachmentEdit, show: AttachmentShow};
export const AttachmentResource = props => (
    <Resource options={{label: '附件'}} name="attachments" {...Attachment} />
);
export default Attachment;
