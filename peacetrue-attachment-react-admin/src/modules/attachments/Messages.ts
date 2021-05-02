import {Messages} from "peacetrue-user";
import {FileMessages} from "peacetrue-file";

export const AttachmentMessages = {
  resources: {
    attachments: {
      name: '附件',
      fields: {
        "remark": "备注",
        ...FileMessages.resources.files.fields,
        ...Messages,
      },
    },
  }
}

export default AttachmentMessages;
