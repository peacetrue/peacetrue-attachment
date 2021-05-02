import * as React from 'react';
import {Create, CreateProps, FileField, FileInput, maxLength, required, SimpleForm, TextInput,} from 'react-admin';
import {FormDataBuilder} from "peacetrue-react-admin";

export const AttachmentCreate = (props: CreateProps) => {
  console.info('AttachmentCreate:', props);
  return (
    <Create transform={(data) => {
      let formData = FormDataBuilder(data);
      if (data.remark) formData.append("remark", data.remark);
      formData.append("_query", JSON.stringify({fileCount: 1}));
      return formData as any;
    }} {...props}>
      <SimpleForm>
        <TextInput source="relativePath" fullWidth validate={[required(), maxLength(255)]}/>
        <FileInput label="附件" source="filePart"
                   minSize={1} maxSize={50 * 1024 * 1024}
                   validate={[required(),]}
                   placeholder={'点击或拖拽上传文件，支持最大 5M'}>
          <FileField source="src" title="title" target={'_blank'}/>
        </FileInput>
        <TextInput source="remark" fullWidth multiline validate={[maxLength(255)]}/>
      </SimpleForm>
    </Create>
  );
};
