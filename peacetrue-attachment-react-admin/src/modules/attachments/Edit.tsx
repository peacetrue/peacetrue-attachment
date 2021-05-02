import * as React from 'react';
import {
  Edit,
  EditProps,
  FunctionField,
  ListButton,
  maxLength,
  required,
  ShowButton,
  SimpleForm,
  TextField,
  TextInput,
  TopToolbar
} from 'react-admin';
import {DownloadButton, SizeRender} from "peacetrue-file";
import {UserCreateFields} from "peacetrue-user";

const AttachmentActions = ({basePath, data}: any) => (
  <TopToolbar>
    <ListButton basePath={basePath} record={data}/>
    <ShowButton basePath={basePath} record={data}/>
    <DownloadButton filePath={data.path}/>
  </TopToolbar>
);

export const AttachmentEdit = (props: EditProps) => {
  console.info('AttachmentEdit:', props);
  return (
    <Edit actions={<AttachmentActions/>}  {...props} >
      <SimpleForm>
        <TextInput source="name" validate={[required(), maxLength(32)]}/>
        <TextField source="path" fullWidth/>
        <FunctionField source="sizes" render={SizeRender}/>
        {/*<TextInput label={'状态编码'} source="stateId" validate={required()}/>*/}
        <TextInput source="remark" fullWidth multiline validate={[maxLength(255)]}/>
        {UserCreateFields}
      </SimpleForm>
    </Edit>
  );
};
