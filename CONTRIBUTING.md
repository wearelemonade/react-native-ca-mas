# Manual de Contribuição

1. Clone o repositório  
`git clone git@github.com/ArmandoAssuncao/react-native-ca-mas.git`

2. Inicie um novo app de teste  
`react-native init CAMASTestApp`  
vá para o app  
`cd CAMASTestApp`  
adicione *react-native-ca-mas* nas dependecias do `package.json`:  
`react-native-ca-mas: ../react-native-ca-mas`  
`yarn install`

3. Esse passo deve ser feito sempre que for reinstalado o modulo.  
Delete `node_modules/react-native-ca-mas` do CAMASTestApp  
`rm -rf node_modules/react-native-ca-mas`  
Crie um link simbolico para o repo:  
`ln -rs ./../react-native-ca-mas/ node_modules/`

4. Rode o projeto de teste CAMASTestApp  
`react-native link`  
`react-native run-ios`  
`react-native run-android`  

5. Faça suas contribuições no diretório do repo.

## Formato das mensagens de commit

Cada mensagem de confirmação deve incluir um **tipo**, um **escopo** e um **assunto**.
Exemplo:

```<tipo>(<escopo>):<assunto>```

As linhas não devem exceder 100 caracteres. Isso permite que a mensagem seja mais fácil de ler no github, bem como em várias ferramentas git e produz um log de confirmação agradável e limpo, ou seja:

```
# 459 page(auth/login): nova página de login
# 463 fix(component/loading): atualização da animação do componente loading modal
# 494 fix(auth/index.nav.ts): correção da importação de comopnentes
# 510 feat(mask_input): nova lib para formatar campos
```

### Tipos

* **feat**: um novo recurso ou nova lib
* **fix**: uma correção de erro
* **page**: uma nova página
* **component**: um novo componente
* **redux**: mudanças com redux
* **doc**: mudança na documentação
* **style**: alterações que afetam nas cores e icones da aplicação de modo 
* **refact**: uma mudança de código que não corrige um bug ou adiciona um recurso
* **test**:  relacionado a tests na aplicação
* **build**: alterações no processo de compilação ou ferramentas auxiliares e bibliotecas, como geração de documentação

#### Escopo

Ideal colocar o endereço da pasta a partir do repositório src, por exemplo: (component/loading)

#### Assunto

O assunto contém descrição sucinta da alteração:
* use o tempo imperativo, atual: "mudança" não "alterada" nem "mudanças"
* não capitalize a primeira letra
* nenhum ponto (.) no final

### Git Workflow

* Crie um ramo seguindo a convenção do nome da filial: `<type>. <Scope>`. **Opcional** Adicione o número de problema relacionado ao seu nome de filial e empurre-o para o repo para adicionar a tag "In Progress" ao problema:

```
git checkout -b <tipo>/<scopo_separado_por_linha>. # <issue-number>
git push origin <tipo>/<scopo_separado_por_linha>. # <issue-number>
```

### Enviando Pull Request

Qualquer coisa que for feito e finalizado, favor abrir um Pull request e seguir o passo a passo
* [Criar uma solicitação de pull request](https://help.github.com/articles/creating-a-pull-request/)
* Definir rótulo(s) e atribuir a alguém
* Tem que ter aprovação de pelo menos 2 pessoas para poder mergear o código na master 
* Se houver um problemas relacionado ao seu pedido, escreva "close #número do comentário" no título do seu PR, *e não mesclar ele até uma nova revisão*
* Se houver conflitos, corrija. Caso o conflito envolva algo que não saiba resolver peça ajuda ao dev envolvido
* Utilize *rebase* para pull, push. Assim mantemos nosso arvore do git organizada
* [Depois de aprovado seu PR por pelo menos 2 pessoas, faça o squash dos commits antes de mergear](https://github.com/blog/2141-squash-your-commits)

### Avaliando uma alteração antes de fazer merge

* Sempre rode o comando: **npm install** depois de fazer ```git pull```
* Teste sempre no Android e iOS, e comente as diferenças caso teve em alguma dessas plataformas
* Anexe as prints do resultado final no card do trello
