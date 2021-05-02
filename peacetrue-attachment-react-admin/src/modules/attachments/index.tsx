import * as React from "react";
import {Resource} from "react-admin";
import AttachmentIcon from '@material-ui/icons/Attachment';
import {AttachmentList} from './List';
import {AttachmentCreate} from './Create';
import {AttachmentEdit} from './Edit';
import {AttachmentShow} from './Show';

export const Attachment = {list: AttachmentList, create: AttachmentCreate, edit: AttachmentEdit, show: AttachmentShow};
export const AttachmentResource = <Resource icon={AttachmentIcon} name="attachments" {...Attachment} />;
export default AttachmentResource;
export * from "./Messages"
