// in src/App.js
import React from 'react';
import {Admin, fetchUtils, Resource} from 'react-admin';
import {AttachmentList} from './modules/attachments/list';
import {AttachmentCreate} from './modules/attachments/create';
import {AttachmentEdit} from './modules/attachments/edit';
import {AttachmentShow} from './modules/attachments/show';
import polyglotI18nProvider from 'ra-i18n-polyglot';
import chineseMessages from 'ra-language-chinese';
import {springDataProvider, springHttpClient} from 'ra-data-spring-rest';

const i18nProvider = polyglotI18nProvider(() => chineseMessages, 'cn');

let httpClient = springHttpClient((url, options = {}) => {
    options.credentials = 'include';
    return fetchUtils.fetchJson(url, options)
        .then(response => {
            let {json} = response;
            if (json.code && json.message) {
                if (json.code !== 'success') {
                    let error = new Error(json.message);
                    error.status = 500;
                    throw error;
                }
                response.json = json.data;
            }
            return response;
        });
});

const dataProvider = springDataProvider('http://localhost:8606', httpClient);
const App = () => (
    <Admin i18nProvider={i18nProvider} dataProvider={dataProvider}>
        <Resource options={{label: '附件'}} name="attachments"
                  list={AttachmentList} create={AttachmentCreate}
                  edit={AttachmentEdit} show={AttachmentShow}/>
    </Admin>
);

export default App;
